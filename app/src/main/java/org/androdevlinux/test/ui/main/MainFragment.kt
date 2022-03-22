package org.androdevlinux.test.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.androdevlinux.test.R
import org.androdevlinux.test.databinding.MainFragmentBinding
import org.androdevlinux.test.databinding.PassPhraseItemBinding
import org.androdevlinux.test.ktx.showCustomPopup


class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val passPhraseAdapter = PassPhraseAdapter()
        binding.apply {
            passPhraseRecyclerView.apply {
                adapter = passPhraseAdapter
            }
            refreshBtn.setOnClickListener {
                viewModel.generateRandom(tvBit39Count.selectedItem.toString().toInt())
            }
            copyPassPhrasesBtn.setOnClickListener {
                val clipboard: ClipboardManager? =
                    requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText(
                    "PassPhrases",
                    viewModel.randomListString.value?.map { it.passPhrase }.toString()
                )
                clipboard?.setPrimaryClip(clip)
                requireContext().showCustomPopup("Pass phrases copied")
            }
            viewModel.generateRandom(tvBit39Count.selectedItem.toString().toInt())
            tvBit39Count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.generateRandom(tvBit39Count.selectedItem.toString().toInt())
                }
            }
        }
        viewModel.randomListString.observe(viewLifecycleOwner) {
            passPhraseAdapter.updateList(it)
        }
    }
}

class PassPhraseAdapter : RecyclerView.Adapter<PassPhraseAdapter.ViewHolder>() {
    private var passPhraseList = listOf<MainViewModel.PassPhrase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(PassPhraseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun updateList(mList: List<MainViewModel.PassPhrase>) {
        passPhraseList = mList
        notifyDataSetChanged()
    }

    // binds items to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = passPhraseList[position]
        holder.binding.tvPhrase.apply {
            text = data.passPhrase
            setBackgroundColor(data.passPhraseColor)
            if (isDark(data.passPhraseColor)) setTextColor(context.getColor(R.color.white)) else setTextColor(context.getColor(R.color.black))
        }
    }

    private fun isDark(color: Int): Boolean = ColorUtils.calculateLuminance(color) < 0.5

    override fun getItemCount(): Int = passPhraseList.size

    class ViewHolder(val binding: PassPhraseItemBinding) : RecyclerView.ViewHolder(binding.root)
}
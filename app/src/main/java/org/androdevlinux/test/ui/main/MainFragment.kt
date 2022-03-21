package org.androdevlinux.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import org.androdevlinux.test.R
import org.androdevlinux.test.databinding.MainFragmentBinding
import org.androdevlinux.test.databinding.PassPhraseItemBinding

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
        binding.passPhraseRecyclerView.apply {
            adapter = passPhraseAdapter
        }
        viewModel.randomListString.observe(viewLifecycleOwner) {
            passPhraseAdapter.updateList(it)
        }

        viewModel.generateRandom(12)

        binding.refreshBtn.setOnClickListener {
            viewModel.generateRandom(12)
        }
    }
}

class PassPhraseAdapter : RecyclerView.Adapter<PassPhraseAdapter.ViewHolder>() {
    private var passPhraseList = listOf<MainViewModel.PassPhrase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            PassPhraseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    fun updateList(mList: List<MainViewModel.PassPhrase>) {
        passPhraseList = mList
        notifyDataSetChanged()
    }

    // binds items to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = passPhraseList[position]
        holder.binding.tvPhrase.apply {
            text = data.passPhrase
            setBackgroundColor(context.resources.getColor(data.passPhraseColor, context.theme))
            if (data.passPhraseColor == R.color.yellow || data.passPhraseColor == R.color.turquoise
                || data.passPhraseColor == R.color.light_brown || data.passPhraseColor == R.color.orange
                || data.passPhraseColor == R.color.light_green
            ) setTextColor(
                context.resources.getColorStateList(R.color.black, context.theme)
            )
            if (data.passPhraseColor == R.color.dark_blue || data.passPhraseColor == R.color.pink ||
                data.passPhraseColor == R.color.black || data.passPhraseColor == R.color.light_blue
                || data.passPhraseColor == R.color.red || data.passPhraseColor == R.color.indigio
            )
                setTextColor(context.resources.getColorStateList(R.color.white, context.theme))
        }
    }

    override fun getItemCount(): Int = passPhraseList.size

    class ViewHolder(val binding: PassPhraseItemBinding) : RecyclerView.ViewHolder(binding.root)
}
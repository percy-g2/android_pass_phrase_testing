package org.androdevlinux.test.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.androdevlinux.test.R
import org.androdevlinux.test.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

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
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pass_phrase_item, parent, false))

    fun updateList(mList: List<MainViewModel.PassPhrase>) {
        passPhraseList = mList
        notifyDataSetChanged()
    }

    // binds items to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = passPhraseList[position]
        holder.textView.apply {
            text = data.passPhrase
            setBackgroundColor(context.resources.getColor(data.passPhraseColor, context.theme))
            if (data.passPhraseColor == R.color.yellow || data.passPhraseColor == R.color.turquoise) setTextColor(context.resources.getColorStateList(R.color.black, context.theme))
           // setTextColor(context.resources.getColorStateList(data.passPhraseColor, context.theme))
        }
    }

    override fun getItemCount(): Int {
        return passPhraseList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_phrase)
    }
}
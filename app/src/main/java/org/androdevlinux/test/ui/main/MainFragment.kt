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

        val customAdapter = CustomAdapter()
        binding.passPhraseRecyclerView.apply {
            adapter = customAdapter
        }
        viewModel.randomListString.observe(viewLifecycleOwner) {
            customAdapter.updateList(it)
        }

        viewModel.generateRandom(12)

        binding.refreshBtn.setOnClickListener {
            viewModel.generateRandom(12)
        }
    }
}

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var passPhraseList = listOf<MainViewModel.PassPhrase>()
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pass_phrase_item, parent, false)

        return ViewHolder(view)
    }

    fun updateList(mList: List<MainViewModel.PassPhrase>) {
        passPhraseList = mList
        notifyDataSetChanged()
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = passPhraseList[position]
        // sets the text to the textview from our itemHolder class
        holder.textView.apply {
            text = ItemsViewModel.passPhrase
            setTextColor(context.resources.getColor(ItemsViewModel.passPhraseColor))
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return passPhraseList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_phrase)
    }
}
package com.example.wallpaperandroid.view.ui.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.wallpaperandroid.R
import com.example.wallpaperandroid.data.model.faq.Ans
import com.example.wallpaperandroid.data.model.faq.Faq
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class HelpAndFaqListAdapter(groups:List<ExpandableGroup<*>>):
    ExpandableRecyclerViewAdapter<ParentViewHolder, ChildListViewHolder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ParentViewHolder {
        val itemView=LayoutInflater.from(parent?.context).inflate(R.layout.help_parent_item,parent,false)
        return ParentViewHolder(itemView)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): ChildListViewHolder {
        val itemView=LayoutInflater.from(parent?.context).inflate(R.layout.help_child_item,parent,false)
        return ChildListViewHolder(itemView)
    }

    override fun onBindChildViewHolder(holder: ChildListViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {
        val faq: Ans = group?.items?.get(childIndex) as Ans
        holder?.bind(faq)
    }

    override fun onBindGroupViewHolder(holder: ParentViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        val faq: Faq = group as Faq
        holder?.bind(faq)
    }
}

class ParentViewHolder(itemView:View):GroupViewHolder(itemView) {
    private val textView:TextView=itemView.findViewById(R.id.questTv)

    fun bind(faq:Faq){
        textView.text=faq.question
    }

}

class ChildListViewHolder(itemView:View):ChildViewHolder(itemView) {
    private val textView:TextView=itemView.findViewById(R.id.answerTv)

    fun bind(ans: Ans){
        textView.text=ans.answer.toString()
    }
}

package com.catatankecilku


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catatankecilku.databinding.ListSelectionViewHolderBinding

//import com.example.clickrush.databinding.ListSelectionViewHolderBinding

class ListSelectionRecycleViewAdapter(private val lists:MutableList<Daftar>) : RecyclerView.Adapter<ListSelectionViewHolder>() {

 //   val listExample=arrayOf("Dendy","Frans","Andreas","Ryan","Deon","Kenny")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        //yg dilakukan saat viewholder dibuat objeknya
        val binding=ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListSelectionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        //mengetahui jumlah item yang ada pada recycleview
        return lists.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.txtId.text=(position+1).toString()+". "
        holder.binding.txtName.text=lists[position].name
    }

    fun listsUpdate(){
        notifyItemInserted(lists.size-1)
    }

}
package ge.gogichaishvili.themovielist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.themovielist.databinding.LayoutGenreItemBinding
import ge.gogichaishvili.themovielist.network.data.Genre

class GenresAdapter(private val itemList: MutableList<Genre>) :
    RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {

    private lateinit var itemClickListener: (Genre) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            LayoutGenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    fun setOnItemCLickListener(clickListener: (Genre) -> Unit) {
        itemClickListener = clickListener
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateAll(list: List<Genre>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val binding: LayoutGenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(genre: Genre) {
            binding.tvName.text = genre.name

            binding.clMain.setOnClickListener {
                itemClickListener.invoke(genre)
            }
        }
    }

}




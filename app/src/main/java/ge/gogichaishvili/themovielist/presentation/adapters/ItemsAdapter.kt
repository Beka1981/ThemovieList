package ge.gogichaishvili.themovielist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gogichaishvili.themovielist.databinding.LayoutMovieItemBinding
import ge.gogichaishvili.themovielist.databinding.LayoutShowItemBinding
import ge.gogichaishvili.themovielist.domain.model.ItemList
import ge.gogichaishvili.themovielist.domain.model.ItemTypesEnum

class ItemsAdapter(private val itemList: MutableList<ItemList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // ItemsAdapter ძალიან ზოგადი სახელია მაგრამ რახან ორივეს ხატავს ok ari

    private lateinit var itemClickListener: (ItemList, Int) -> Unit

    private val VIEW_TYPE_MOVIES = ItemTypesEnum.MOVIES

    fun setOnItemCLickListener(clickListener: (ItemList, Int) -> Unit) {
        itemClickListener = clickListener
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(itemList_: List<ItemList>) {
        itemList.clear()
        itemList.addAll(itemList_)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(private val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemList: ItemList) {
            binding.tvMovieName.text = itemList.originalTitle
            binding.ivPoster.setImageURI("https://image.tmdb.org/t/p/w500${itemList.posterPath}")
            binding.tvRating.text = itemList.voteAverage.toString()
            binding.tvReleaseDate.text = itemList.releaseDate
            binding.root.setOnClickListener {
                itemClickListener.invoke(itemList, adapterPosition)
            }
        }
    }

    inner class ShowsViewHolder(private val binding: LayoutShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemList: ItemList) {
            binding.tvMovieName.text = itemList.originalTitle
            binding.ivPoster.setImageURI("https://image.tmdb.org/t/p/w500${itemList.posterPath}")
            binding.tvRating.text = itemList.voteAverage.toString()
            binding.tvReleaseDate.text = itemList.releaseDate
            binding.root.setOnClickListener {
                itemClickListener.invoke(itemList, adapterPosition)
            }
        }
    }

    // ჯობდა რომ რამე აბსტრაქტული კლასი გაგეკეთებინა რომელიც ამ ორი ვიუჰოლდერის მშობელი იქნებოდა
    // აღარ მოგიწევდა onBindViewHolder ში დაკასტვა

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_MOVIES.value) {
            return MoviesViewHolder(
                LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        return ShowsViewHolder(
            LayoutShowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (itemList[position].viewType === VIEW_TYPE_MOVIES) {
            (holder as MoviesViewHolder).bindData(itemList[position])
        } else {
            (holder as ShowsViewHolder).bindData(itemList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].viewType.value
    }

}

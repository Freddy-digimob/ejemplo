package com.task.ui.component.recipes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.dto.recipes.RecipesItem
import com.task.databinding.RecipeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.recipes.RecipesListViewModel

/**
 * Created by AhmedEltaher
 */

class RecipesAdapterHorizontal(val context: Context, private val recipes: List<RecipesItem>) : RecyclerView.Adapter<RecipesAdapterHorizontal.MainRecyclerView>() {

    class MainRecyclerView(itemView: View): RecyclerView.ViewHolder(itemView){
        var tv_name: TextView? = null
        var tv_caption: TextView? = null
        var image: ImageView? = null

        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_caption = itemView.findViewById(R.id.tv_caption)
            image = itemView.findViewById(R.id.iv_recipe_item_image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerView {
        //val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //return RecipeViewHolder(itemBinding)
        return MainRecyclerView(LayoutInflater.from(context).inflate(R.layout.recipe_item_horizontal, parent, false))
    }

    override fun onBindViewHolder(holder: MainRecyclerView, position: Int) {
        //holder.bind(recipes[position], onItemClickListener)
        holder.tv_name?.setText(recipes.get(position).name)
        holder.tv_caption?.setText(recipes.get(position).description)
        Picasso.get().load(recipes.get(position).thumb).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(holder.image)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}


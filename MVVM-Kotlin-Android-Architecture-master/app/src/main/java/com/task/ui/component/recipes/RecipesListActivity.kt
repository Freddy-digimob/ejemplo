package com.task.ui.component.recipes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.task.R
import com.task.RECIPE_ITEM_KEY
import com.task.data.Resource
import com.task.data.dto.recipes.Recipes
import com.task.data.dto.recipes.RecipesItem
import com.task.data.error.SEARCH_ERROR
import com.task.databinding.HomeActivityBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.details.DetailsActivity
import com.task.ui.component.recipes.adapter.RecipesAdapter
import com.task.ui.component.recipes.adapter.RecipesAdapterHorizontal
import java.util.concurrent.TimeUnit
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_activity.*

/**
 * Created by AhmedEltaher
 */
@AndroidEntryPoint
class RecipesListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val recipesListViewModel: RecipesListViewModel by viewModels()
    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var recipesAdapter2: RecipesAdapterHorizontal
    var mHandler: Handler? = null
    var mRunnable: Runnable? = null
    var layoutManager2: LinearLayoutManager? = null

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.recipe)
        val layoutManager = LinearLayoutManager(this)

        layoutManager2 = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)

        binding.rvRecipesList2.layoutManager = layoutManager2

        recipesListViewModel.getRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> recipesListViewModel.getRecipes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = VISIBLE
            recipesListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(recipes: Recipes) {
        if (!(recipes.recipesList.isNullOrEmpty())) {

            recipesAdapter = RecipesAdapter(recipesListViewModel, recipes.recipesList)
            binding.rvRecipesList.adapter = recipesAdapter

            recipesAdapter2 = RecipesAdapterHorizontal(this, recipes.recipesList)
            binding.rvRecipesList2.adapter = recipesAdapter2

            var snapHelper: SnapHelper = PagerSnapHelper()
            binding.rvRecipesList2?.clearOnScrollListeners()
            binding.rvRecipesList2?.setOnFlingListener(null)
            snapHelper.attachToRecyclerView(binding.rvRecipesList2)
            binding.indicator!!.attachToRecyclerView(binding.rvRecipesList2!!)

            var scrollSpeed = 8000
            mHandler = Handler()
            mRunnable = Runnable {
                var x = 700
                var flag = true
                var scrollPosition = layoutManager2?.findLastCompletelyVisibleItemPosition()
                var arraySize = recipes.recipesList.size

                if (scrollPosition != null) {
                    if (scrollPosition < arraySize) {
                        if(scrollPosition == arraySize -1) {
                            flag = false
                        } else {
                            if(scrollPosition <= 1) {
                                flag = true
                            }
                        }

                        if(!flag) {
                            try {
                                TimeUnit.SECONDS.sleep(1)
                                binding.rvRecipesList2?.scrollToPosition(0)
                            } catch (e: InterruptedException){
                                e.printStackTrace()
                            }
                        }

                        scrollPosition = layoutManager2?.findFirstCompletelyVisibleItemPosition()
                        binding.rvRecipesList2?.smoothScrollBy(x,0)
                        mHandler!!.postDelayed(mRunnable!!, scrollSpeed.toLong())
                    }
                }
            }
            mHandler!!.postDelayed(mRunnable!!, scrollSpeed.toLong())


            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<RecipesItem>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(RECIPE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        recipesListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }


    private fun showSearchResult(recipesItem: RecipesItem) {
        recipesListViewModel.openRecipeDetails(recipesItem)
        binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleRecipesList(status: Resource<Recipes>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(recipes = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { recipesListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(recipesListViewModel.recipesLiveData, ::handleRecipesList)
        observe(recipesListViewModel.recipeSearchFound, ::showSearchResult)
        observe(recipesListViewModel.noSearchFound, ::noSearchResult)
        observeEvent(recipesListViewModel.openRecipeDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(recipesListViewModel.showSnackBar)
        observeToast(recipesListViewModel.showToast)

    }
}

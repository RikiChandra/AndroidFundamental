package com.example.githubapp.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.example.githubapp.R
import com.example.githubapp.UserGithub
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.view.favorite.FavoriteActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AdapterList
    private var listUsergit = ArrayList<UserGithub>()
    private lateinit var recyclerView: RecyclerView
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private var searchQuery: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val loadingProgressBar = findViewById<ProgressBar>(R.id.loading)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        recyclerView = findViewById(R.id.RvData)
        adapter = AdapterList(listUsergit)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        viewModel.users.observe(this) { users ->
            setUserData(users)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.isNotFound.observe(this) { isNotFound ->
            if (isNotFound) {
                viewModel.getUserSearch(randomText(2))
                Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        if (savedInstanceState == null) {
            viewModel.getUserSearch(randomText(2))
        } else {
            searchQuery = savedInstanceState.getString("searchQuery", "")
            if (searchQuery.isNotEmpty()) {
                viewModel.getUserSearch(searchQuery)
            }
            val userList = savedInstanceState.getParcelableArrayList<UserGithub>("userList")
            if (userList != null) {
                listUsergit.addAll(userList)
                adapter.notifyDataSetChanged()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        menu.clear()
        inflater.inflate(R.menu.option_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                query.let{
                    binding?.RvData?.visibility = View.VISIBLE
                    viewModel.getUserSearch(query)
                    setUserData(listUsergit)
                    searchView.clearFocus()
                    searchQuery = query
                }
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchQuery", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchQuery = savedInstanceState.getString("searchQuery").toString()
        viewModel.getUserSearch(searchQuery)
    }





    private fun setUserData(users: List<UserGithub>) {
        listUsergit.clear()
        listUsergit.addAll(users)
        adapter.notifyDataSetChanged()

    }


    private fun randomText(length: Int): String {
        val abjad = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
            .map { abjad.random() }
            .joinToString("")
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            recyclerView.adapter = null
            recyclerView.layoutManager = null
        } catch (e: Exception) {
            Log.e("MainActivity", "Error while closing resources: ${e.message}")
        }
    }
}








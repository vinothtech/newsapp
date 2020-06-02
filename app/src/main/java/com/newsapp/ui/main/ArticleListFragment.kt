package com.newsapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.data.model.Articles
import com.newsapp.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.source_list_fragment.*

class ArticleListFragment : Fragment() {

    private var sourceId: String = "0"
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sourceId = arguments?.getString("sourceid", "0") ?: "0"
        mainViewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.source_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(context);
        articleList.layoutManager = layoutManager
        adapter = ArticleListAdapter(ArrayList<Articles>(),
            object : ArticleListAdapter.ItemClickListener {
                override fun onItemClicked(article: Articles) {
                    startDetailsActivity(article.articleUrl);
                }
            });
        articleList.adapter = adapter
        setObservables();
        mainViewModel.getArticles(sourceId);
    }


    fun setObservables() {
        mainViewModel.articleListLiveData.observe(this, object : Observer<List<Articles>> {
            override fun onChanged(newList: List<Articles>) {
                val updatedList = newList?.filter { it.source.id == sourceId }
                adapter.updateList(updatedList)
            }
        })
    }

    fun startDetailsActivity(url: String) {
        val bundle = Bundle();
        bundle.putString("url", url)
        val intent = Intent(context, DetailsActivity::class.java);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
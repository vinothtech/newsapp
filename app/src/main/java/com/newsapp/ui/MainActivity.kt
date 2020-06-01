package com.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.newsapp.R
import com.newsapp.data.model.Sources
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupObservables()
        mainViewModel.getSourcesList()
    }

    private fun setupObservables() {
        mainViewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) {
                progressBar.visibility = View.VISIBLE;
            } else {
                progressBar.visibility = View.GONE;
            }
        })

        mainViewModel.sourceList.observe(this, Observer {
            it.run() {
                if (it.size > 0) {
                    setupViewPager(pager, it)
                    tabLayout.setupWithViewPager(pager)
                }
            }
        })

    }

    private fun setupViewPager(viewPager: ViewPager, sourceList: List<Sources>) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(sourceList)
        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager) {
        private val sourceList = ArrayList<Sources>();
        override fun getItem(position: Int): Fragment {

            val articleFragmet = ArticleListFragment()
            val args = Bundle()
            args.putString("sourceid", sourceList[position].id)
            articleFragmet.arguments = args;
            return articleFragmet
        }

        override fun getCount(): Int {
            return sourceList.size
        }

        fun addFragment(updatedSourceList: List<Sources>) {
            sourceList.addAll(updatedSourceList)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return sourceList[position].name
        }
    }


}

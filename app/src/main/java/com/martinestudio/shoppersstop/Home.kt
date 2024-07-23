package com.martinestudio.shoppersstop

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.martinestudio.api.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment(),MyAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    private lateinit var productArrayList: ArrayList<MyData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.homeRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, true))
//        myAdapter = MyAdapter(this, productArrayList)

        fetchProductData()

        val moreButton = view.findViewById<ImageView>(R.id.btnSideBar)
        val buttonProfile = view.findViewById<ImageView>(R.id.btnProfile)


        return view
    }


    private fun fetchProductData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val productList = responseBody.products
                        myAdapter = MyAdapter(this@Home, productList)
                        recyclerView.adapter = myAdapter
                    } else {
                        Log.d("Home", "Response body is null")
                    }
                } else {
                    Log.d("Home", "Response failed")
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Home", "onFailure: " + t.message)
            }
        })


    }

    override fun onItemClick(position: Int) {
        val product = productArrayList[position]
//        val fragment = ProductDetailFragment.newInstance(product)
        parentFragmentManager.beginTransaction()
//            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

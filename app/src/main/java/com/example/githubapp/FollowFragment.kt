package com.example.githubapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowBinding



class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var login: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_LOGIN)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowAdapter(arrayListOf())
        layoutManager = LinearLayoutManager(requireContext())
        binding.Rvdatas.apply {
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
            layoutManager = this@FollowFragment.layoutManager
        }

        login?.let {
            if (arguments?.getInt(ARG_POSITION) == 1) {
                getFollowers(it)
            } else {
                getFollowing(it)
            }
        }
    }

    private fun getFollowers(username: String) {
        val viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        viewModel.getFollowers(username)

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.apply {
                listUser.clear()
                listUser.addAll(it)
                notifyDataSetChanged()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loadings.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun getFollowing(username: String) {
        val viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        viewModel.getFollowing(username)

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.apply {
                listUser.clear()
                listUser.addAll(it)
                notifyDataSetChanged()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.loadings.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_LOGIN = "login"

        @JvmStatic
        fun newInstance(position: Int, login: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_LOGIN, login)
                }
            }
    }
}

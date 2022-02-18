/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener {
            view:View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "NumCorrect: ${args.numCorrectQuestions}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_SHORT).show()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getSharedIntent() :Intent
    {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val sharedIntent = Intent(Intent.ACTION_SEND)//Intent to deliver the message to the user to share
        sharedIntent.setType("text/plain").putExtra(
            Intent.EXTRA_TEXT,getString(R.string.share_success_text, args.numCorrectQuestions, args.numQuestions)//The actual data to be delivered will be put in the EXTRA_TEXT
        )
        return sharedIntent
    }

    private fun sharedSuccess()
    {
        startActivity(getSharedIntent())//Begin sharing
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        if(getSharedIntent().resolveActivity(requireActivity().packageManager) == null)
        //If null -> sharedIntent does not resolve => find the sharing menu item from the inflated menu and make the menu item visible
        {
            !menu.findItem(R.id.share).isVisible
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.share -> sharedSuccess()//check the sharing action is success
        }
        return super.onOptionsItemSelected(item)
    }
}

package dev.robert.bagelly.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.bagelly.R
import dev.robert.bagelly.databinding.FragmentSettingsBinding
import dev.robert.bagelly.ui.fragments.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var listItem: Array<String>
    private val viewModel by viewModels<SettingsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.settingsToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listItem = resources.getStringArray(R.array.settings_list)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1, android.R.id.text1, listItem
        )
        binding.settingsListView.adapter = adapter

        binding.settingsListView.onItemClickListener =

            OnItemClickListener { _, _, position, _ ->
                val value = adapter.getItem(position)
                if (value == "App Theme"){
                    //Start Theme selection dialog
                    val options = resources.getStringArray(R.array.themes)
                    val singleChoiceDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Choose one from the below")
                        .setSingleChoiceItems(options, 0){ _, i ->
                            //requireActivity().setTheme(R.style.AppTheme)
                            requireActivity().recreate()
                            Toast.makeText(requireContext(), options[i], Toast.LENGTH_SHORT).show()
                        }.create()
                    singleChoiceDialog.show()
                }
                else if (value == "Share this App"){
                    //Share app
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Bagellly")
                    intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=dev.robert.bagelly")
                    startActivity(Intent.createChooser(intent, "Share via"))
                }
                else if (value == "Language"){
                    //start language dialog
                    val options = resources.getStringArray(R.array.languages)
                    val singleChoiceDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Choose your preferred language")
                        .setSingleChoiceItems(options, 0){ _, i ->
                            //requireActivity().setTheme(R.style.AppTheme)
                            requireActivity().recreate()
                            Toast.makeText(requireContext(), options[i], Toast.LENGTH_SHORT).show()
                        }.create()
                    singleChoiceDialog.show()
                }
                else if (value == "Logout"){
                    val alertDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Are you sure?")
                        .setMessage("This will log you out of the app")
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton("Yes"){ _, i ->
                            try {
                                val isLoggedOut = viewLifecycleOwner.lifecycleScope.launch {
                                    viewModel.logout()
                                }
                                if (isLoggedOut.isActive){
                                    isLoggedOut.cancel()
                                }
                                else{
                                    Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                                }
                            }
                            catch (e: Exception){
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                            }

                        }
                        .setNegativeButton("No"){ _, i ->
                            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
                        }.create()
                    alertDialog.show()
                }
            }

        return view
    }


}
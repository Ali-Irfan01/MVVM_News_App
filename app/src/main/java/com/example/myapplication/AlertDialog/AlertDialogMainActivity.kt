package com.example.myapplication.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAlertDialogMainBinding

class AlertDialogMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlertDialogMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertDialogMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addContactDialog = AlertDialog.Builder(this)
            .setTitle("Add contact")
            .setMessage("Do you want to add Mr. Poop to your contacts list?")
            .setIcon(R.drawable.ic_add_contact)
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(
                    this,
                    "You have added this person to contact list",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("No, thanks") { _, _ ->
                Toast.makeText(this, "This contact was not added", Toast.LENGTH_SHORT).show()
            }

        binding.btnDialog1.setOnClickListener {
            addContactDialog.show()
        }

        val options = arrayOf("First Item", "Second Item", "Third Item")
        val singleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose one of these options")
            .setSingleChoiceItems(options, 0)   {dialogInterface, i ->
                Toast.makeText(this, "You Clicked on ${options[i]}", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(
                    this,
                    "You have accepted Single Choice Dialog",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Decline"){_, _ ->
                Toast.makeText(
                    this,
                    "You have declined Single Choice Dialog",
                    Toast.LENGTH_SHORT
                ).show()
            }

        binding.btnDialog2.setOnClickListener{
            singleChoiceDialog.show()
        }

        val multiChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Choose one of these options")
            .setMultiChoiceItems(options, booleanArrayOf(false, false, false)) { _, i, isChecked ->
                if (isChecked) {
                    Toast.makeText(
                        this,
                        "You checked ${options[i]}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "You unchecked ${options[i]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setPositiveButton("Accept"){ _, _ ->
                Toast.makeText(
                    this,
                    "You have ACCEPTED Multi Choice Dialog",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Decline"){_, _ ->
                Toast.makeText(
                    this,
                    "You have DECLINED Mu Cltihoice Dialog",
                    Toast.LENGTH_SHORT
                ).show()
            }

        binding.btnDialog3.setOnClickListener{
            multiChoiceDialog.show()
        }

    }
}
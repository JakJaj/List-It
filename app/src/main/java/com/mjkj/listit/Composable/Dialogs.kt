package com.mjkj.listit.Composable

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mjkj.listit.Activity.FilledListsListActivity
import com.mjkj.listit.Activity.FilledTasksTaskActivity
import com.mjkj.listit.Activity.MainActivity
import com.mjkj.listit.Model.ListOfTasks
import com.mjkj.listit.Model.Task
import com.mjkj.listit.Model.User

@Composable
fun CreateTaskDialog(onDismissRequest: () -> Unit,listCode:String, parentActivity: Activity){
    val db = Firebase.firestore
    androidx.compose.ui.window.Dialog(onDismissRequest ={ onDismissRequest() }){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .padding(10.dp),

            shape = RoundedCornerShape(16.dp),
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Create a new task", fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(20.dp))
                val taskName: String = OutlinedTextField("Task Name (Required)")
                Spacer(modifier = Modifier.padding(5.dp))
                val taskDescription: String = OutlinedTextField("Task Description")
                Spacer(modifier = Modifier.padding(15.dp))
                HorizontalDivider(modifier = Modifier.height(5.dp))
                Spacer(modifier = Modifier.padding(10.dp))

                ButtonFilled("Create") {
                    Log.d("D", "TaskName: $taskName")
                    Log.d("D", "TaskDescription: $taskDescription")

                    if (taskName != "") {
                        val user = User.createUser(
                            Firebase.auth.currentUser?.uid,
                            Firebase.auth.currentUser?.displayName.toString(),
                            Firebase.auth.currentUser?.email.toString()
                        )
                        val task = Task.createTask(taskName, user, taskDescription)
                        if(task.getTaskName() != ""){
                            Log.d("D", "User: $user id: ${user.getId()} name: ${user.getName()} email: ${user.getEmail()} lists: ${user.getLists()}")
                            Log.d("D", "Task: ${task.getTaskName()} creator: ${task.getCreator()?.getName()} description: ${task.getDescription()} status: ${task.getStatus()}")
                            val hashTask = hashMapOf(
                                "taskName" to taskName,
                                "description" to taskDescription,
                                "creator" to user.getId(),
                                "status" to task.getStatus()
                            )

                            db.collection("tasks").document(task.getCode()).set(hashTask)

                            db.collection("lists").document(listCode).get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        val tasks = document.data?.get("tasks")
                                        if (tasks == null) {
                                            val tempTasks = mutableListOf(task.getCode())
                                            db.collection("lists").document(listCode).update("tasks", tempTasks)
                                        } else {
                                            val tempTasks = document.data?.get("tasks") as MutableList<String>
                                            tempTasks.add(task.getCode())
                                            db.collection("lists").document(listCode).update("tasks", tempTasks)
                                        }

                                        val intent = Intent(parentActivity, FilledTasksTaskActivity::class.java)
                                        intent.putExtra("listCode", listCode)
                                        ContextCompat.startActivity(parentActivity, intent, null)
                                        parentActivity.finish()

                                    }
                                }
                        }
                        else{
                            Toast.makeText(parentActivity, "List name is required", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
@Composable
        /** This is a composable function that creates a dialog box
         *
         * @param onDismissRequest: () -> Unit - The action to be performed when the dialog is dismissed
         * */
fun CreateOrJoinDialog(onDismissRequest: () -> Unit, parentActivity: Activity) {
    var showCreateListContent by remember { mutableStateOf(false) }

    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(650.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextButton(onClick = { showCreateListContent = false }) {
                        Text(text = "Join List")
                    }
                    TextButton(onClick = { showCreateListContent = true }) {
                        Text(text = "Create List")
                    }
                }
                HorizontalDivider()
                if (showCreateListContent) {
                    CreateListContent(parentActivity)

                } else {
                    JoinListContent(parentActivity)
                }
            }
        }
    }
}

@Composable
        /** This is a composable function that joins to an existing a new list
         *
         * */

fun JoinListContent(parentActivity: Activity){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Join an existing list", fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(25.dp))
        val listCode: String = OutlinedTextField("List Code")
        Spacer(modifier = Modifier.padding(20.dp))


        Text(text = "Enter the code of the list you want to join", fontSize = 14.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        HorizontalDivider(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.padding(75.dp))
        ButtonFilled("Join") {
            Log.d("D", "JoinListCode: $listCode")
            if(listCode.length != 6){
                Toast.makeText(parentActivity, "Codes contain 6 symbols", Toast.LENGTH_SHORT).show()
            }
            else{
                val db = Firebase.firestore
                val user = User.createUser(
                    Firebase.auth.currentUser?.uid,
                    Firebase.auth.currentUser?.displayName.toString(),
                    Firebase.auth.currentUser?.email.toString())
                db.collection("lists").document(listCode).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            db.collection("users").document(Firebase.auth.currentUser!!.uid)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        val currentList = document.data?.get("lists")
                                        if (currentList == null) {
                                            val tempCurrentList = mutableListOf<String>()
                                            tempCurrentList.add(listCode)
                                            db.collection("users")
                                                .document(Firebase.auth.currentUser!!.uid)
                                                .update("lists", tempCurrentList)
                                        } else {
                                            val tempCurrentList =
                                                document.data?.get("lists") as MutableList<String>
                                            if (!tempCurrentList.contains(listCode)) {
                                                tempCurrentList.add(listCode)
                                                db.collection("users")
                                                    .document(Firebase.auth.currentUser!!.uid)
                                                    .update("lists", tempCurrentList)
                                            } else {
                                                Toast.makeText(
                                                    parentActivity,
                                                    "List already exists",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                    val intent = Intent(parentActivity, FilledListsListActivity::class.java)
                                    ContextCompat.startActivity(parentActivity, intent, null)
                                    parentActivity.finish()
                                }
                        } else {
                            Toast.makeText(parentActivity, "List not found", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

@Composable
        /** This is a composable function that creates submenu for creating a new list
         *
         * */
fun CreateListContent(parentActivity: Activity){
    val colorArray = arrayOf(
        "Red",
        "Blue",
        "Green",
        "Yellow",
        "Cyan",
        "Pink",
        "Gray",
        "Orange",
        "Purple",
        "Light Blue",
        "Burgundy",
        "Dark Green"
    )
    val db = Firebase.firestore
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Create a new list", fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        val listName: String = OutlinedTextField("List Name (Required)")

        Spacer(modifier = Modifier.padding(5.dp))
        val shortDescription: String = OutlinedTextField("Short Description ")
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "Choose a color for your list", fontSize = 14.sp)
        val item: String = DropdownMenuBox(colorArray)
        Spacer(modifier = Modifier.padding(15.dp))
        HorizontalDivider(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        ButtonFilled("Create") {

            Log.d("D", "ListName: $listName")
            Log.d("D", "ShortDescription: $shortDescription")
            Log.d("D", "Color: $item")

            
            if(listName != ""){
                val user = User.createUser(
                    Firebase.auth.currentUser?.uid,
                    Firebase.auth.currentUser?.displayName.toString(),
                    Firebase.auth.currentUser?.email.toString())
                val list = ListOfTasks.createList(listName, user, item, shortDescription)
                Log.d("D", "User: $user id: ${user.getId()} name: ${user.getName()} email: ${user.getEmail()} lists: ${user.getLists()}")
                val hashList = hashMapOf(
                    "listName" to listName,
                    "code" to list.getCode(),
                    "color" to item,
                    "description" to shortDescription,
                    "tasks" to null,
                    "creator" to user.getId()
                )
                db.collection("lists").document(list.getCode()).set(hashList)

                db.collection("users").document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val currentList = document.data?.get("lists")

                            if(currentList == null){
                                val currentList = mutableListOf<String>()
                                currentList.add(list.getCode())
                                db.collection("users").document(Firebase.auth.currentUser!!.uid).update("lists", currentList)
                            }
                            else{
                                val currentList = document.data?.get("lists") as MutableList<String>
                                currentList.add(list.getCode())
                                db.collection("users").document(Firebase.auth.currentUser!!.uid).update("lists", currentList)
                            }
                            val intent = Intent(parentActivity, FilledListsListActivity::class.java)
                            ContextCompat.startActivity(parentActivity, intent, null)
                        }
                    }
            }
            else{
                Toast.makeText(parentActivity, "List name is required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun changeState(state: MutableState<Boolean>): Boolean {
    state.value = !state.value
    return state.value
}

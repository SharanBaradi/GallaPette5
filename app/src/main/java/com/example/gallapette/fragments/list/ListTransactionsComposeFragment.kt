package com.example.gallapette.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gallapette.R
import com.example.gallapette.data.model.CashFlow
import com.example.gallapette.data.viewmodel.CashFlowViewModel
import com.example.jetpackcompose_basics.ui.theme.JetpackCompose_BasicsTheme
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

class ListTransactionsComposeFragment : Fragment() {

    private lateinit var mCashFlowViewModel: CashFlowViewModel
    private lateinit var cashFlowList: List<CashFlow>
    private val model: CashFlowViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        var composeview: View = ComposeView(requireContext()).apply {
//            setContent {
//                JetpackCompose_BasicsTheme {
//
//                }
//            }
//        }

        cashFlowList =
            listOf(CashFlow(0, 0, "Payer", "Acc", "Beneficiary", "Acc", "Payment Description"))
//        val adapter = CustomListAdapter()
//        mCashFlowViewModel = ViewModelProvider(this).get(CashFlowViewModel::class.java)
//        mCashFlowViewModel.readAllData.observe(viewLifecycleOwner, Observer { cashflow ->
//            println("cashFlow Data Changed")
////            adapter.setCashFlowUpdatedData(cashflow)
//            composeview = ComposeView(requireContext()).apply {
//                setContent {
//                    ListTransactions(cashFlow = cashflow)
//                }
//            }
//        })
//
//        sleep(1000)
//        return composeview
        mCashFlowViewModel = ViewModelProvider(this).get(CashFlowViewModel::class.java)
        return ComposeView(requireContext()).apply {
            setContent {
                JetpackCompose_BasicsTheme {
                    mCashFlowViewModel.readAllData.observe(viewLifecycleOwner, Observer { cashflow ->
                        println("cashFlow Data Changed")
                       cashFlowList = cashflow
                    })
                    ListTransactions(cashFlow = cashFlowList)
                }
            }
        }
    }
}

@Composable
fun listCard(cashFlow: CashFlow) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = cashFlow.Beneficiary,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = cashFlow.Description,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

//@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
//@Composable
//fun PreviewMessageCard() {
//    JetpackCompose_BasicsTheme {
//        MessageCard(
//            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
//        )
//    }
//}
@Composable
fun listHeader() {
    Text(text = "My Header")
}

@Preview
@Composable
fun PreviewlistHeader() {
    listHeader()
}

@Composable
fun ListTransactions(cashFlow: List<CashFlow>) {
    LazyColumn {
        items(cashFlow) { listItem ->
            listCard(listItem)
        }
    }
}

//@Preview
//@Composable
//fun PreviewConversation() {
//    JetpackCompose_BasicsTheme {
//        ListTransactions()
//    }
//}

/*


@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}





data class Message(val author: String, val body: String)


//@Preview(name = "Light Mode")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
//@Composable
//fun PreviewMessageCard() {
//    JetpackCompose_BasicsTheme {
//        MessageCard(
//            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
//        )
//    }
//}


@Composable
fun listHeader(){
    Text(text = "My Header")
}

@Preview
@Composable
fun PreviewlistHeader(){
    listHeader()
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            if(message.body == "Searching for alternatives to XML layouts..."){
                listHeader()
            }
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    JetpackCompose_BasicsTheme {
        Conversation(SampleData.conversationSample)
    }
}





*/
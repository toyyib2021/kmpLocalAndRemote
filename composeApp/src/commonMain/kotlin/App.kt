import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import andriodanddesktoptutorial.composeapp.generated.resources.Res
import andriodanddesktoptutorial.composeapp.generated.resources.compose_multiplatform
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.Product
import kotlinx.coroutines.launch
import list.ListComponent


@Composable
@Preview
fun App() {

    MaterialTheme {
//        AppContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    products: State<ListComponent.Model>,
    onItemClicked: (Product) -> Unit
){

//    val  products = homeViewModel.product.collectAsState()


    BoxWithConstraints {
        val  scope = this
        val maxWith = scope.maxWidth

        var col = 2
        var modifier = Modifier.fillMaxWidth()
        if (maxWith > 840.dp){
            col = 3
            modifier = Modifier.widthIn(max = 1080.dp)
        }

        var scrollState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            LazyVerticalGrid(
                modifier = Modifier.draggable(orientation = Orientation.Vertical, state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }),
                columns = GridCells.Fixed(col),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ){
                item (
                    span ={ GridItemSpan(col) }
                ){
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = "",
                        active = false,
                        onActiveChange = {},
                        onQueryChange = {},
                        onSearch = {},
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
                        placeholder = { Text("Search Product") }
                    ){

                    }
                }

                    items(
                        items = products.value.items,
                        key = {it ->
                            it.id.toString()}
                    ){ product ->
                        Card(
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                                .clickable { onItemClicked(product) },
                            shape = RoundedCornerShape(15.dp),
                            elevation = 2.dp
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val painter = rememberImagePainter(product.image.toString())

                                Image(
                                    modifier = Modifier.height(150.dp)
                                        .padding(8.dp),
                                    painter = painter,
                                    contentDescription = "image",
                                )
                                Text(
                                    modifier = Modifier.padding(6.dp).heightIn(min = 40.dp),
                                    text = product.title.toString(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    modifier = Modifier.padding(6.dp).heightIn(min = 40.dp),
                                    text = "${product.price.toString()} NR",
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }


            }
        }
    }
}
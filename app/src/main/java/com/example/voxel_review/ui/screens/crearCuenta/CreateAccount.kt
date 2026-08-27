package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.utils.MostrarLogo
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.home.components.NombreApp
import com.example.voxel_review.ui.theme.VoxelPrimary
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import com.example.voxel_review.ui.theme.VoxelSecondary
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.onPrimaryDark
import com.example.voxel_review.ui.theme.onSurfaceDark
import android.R.attr.shape

@Composable
fun CreateAccountContent(
    modifier: Modifier = Modifier
) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    val icono =
        if (!mostrarContrasena) {
            R.drawable.img_no_ver_contrasena
        } else {
            R.drawable.visible
        }

    Box(
        modifier = modifier.fillMaxWidth()
    ){
        //columna para logo y nombre app
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            MostrarLogo(
                modifier = Modifier.width(120.dp).
                offset(y = 50.dp)
            )
            Spacer(
                modifier = Modifier.height(47.dp)
            )
            NombreApp(
                modifier = Modifier.width(170.dp)
            )
        }
        Spacer(
            modifier = Modifier.height(100.dp)
        )
        //column para mensaje crear cuenta voxel
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = 230.dp).fillMaxWidth()
        )
        {
            Text(
                text = "Crear",
                color = VoxelPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Cuenta Voxel",
                color = VoxelSecondary,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().
            offset(y = 380.dp)
        ) {
            formularioCreateAccount(
                userName = userName,
                icono = R.drawable.img_username,
                onUserChange = { userName = it },
                modifier = Modifier.width(400.dp)
            )
        }


    }

}



@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()){
        FondoPantalla()
        CreateAccountContent(modifier = modifier.fillMaxWidth())
    }
}

@Preview (showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}

@Composable
fun formularioCreateAccount(
    userName: String,
    icono: Int,
    onUserChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = userName,
        onValueChange = onUserChange,

        placeholder = {
            Text(
                text = "username",
                color = VoxelTextSecondary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = onPrimaryDark,
            unfocusedContainerColor = onPrimaryDark,
            focusedTextColor = onSurfaceDark,
            unfocusedTextColor = onSurfaceDark
        ),

        trailingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(icono),
                    contentDescription = "icono juego"
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
    )
}

@Composable
@Preview
fun formularioCreateAccountPreview(){
    formularioCreateAccount(
        userName = "",
        icono = R.drawable.img_no_ver_contrasena,
        onUserChange = {}
    )
}
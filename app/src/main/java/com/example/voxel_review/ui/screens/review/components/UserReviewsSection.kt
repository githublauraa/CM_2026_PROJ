package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White

/**
 * Sección que recopila las opiniones y puntuaciones dejadas por la comunidad de usuarios.
 *
 * @param username Nombre del usuario que realizó la reseña.
 * @param comentario Comentario realizado por el usuario.
 * @param rating Calificación dada por el usuario.
 * @param onClickReview Acción ejecutada al presionar "Ver todas".
 * @param modifier Modificador para personalizar el contenedor de comentarios.
 * @param cardBackgroundColor Color de fondo aplicado a la tarjeta de comentario.
 */
@Composable
fun UserReviewsSection(
    username: String,
    comentario: String,
    rating: Int,
    onClickReview: () -> Unit,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.resenas_usuarios),
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.ver_todas),
                color = VoxelPrimary,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    onClickReview()
                }
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardBackgroundColor
            )
        ) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {

                    Image(
                        painter = painterResource(
                            R.drawable.imagen_login_user
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = username,
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        StarRating(
                            rating = rating
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = comentario,
                        color = VoxelTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserReviewsSectionPreview() {

    UserReviewsSection(
        username = "GamerX Pro",
        comentario = "Una experiencia increíble. La historia y la jugabilidad destacan bastante.",
        rating = 4,
        onClickReview = {},
        cardBackgroundColor = Color.LightGray
    )
}
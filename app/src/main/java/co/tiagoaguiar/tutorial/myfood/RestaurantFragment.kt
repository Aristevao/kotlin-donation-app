package co.tiagoaguiar.tutorial.myfood

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.atway.ui.adapter.ATAdapter
import co.tiagoaguiar.tutorial.myfood.databinding.FragmentRestaurantBinding

class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

  private var binding: FragmentRestaurantBinding? = null

  private val categoryAdapter = ATAdapter({ CategoryView(it) })
  private val bannerAdapter = ATAdapter({ BannerView(it) })
  private val shopAdapter = ATAdapter({ ShopView(it) })
  private val moreShopAdapter = ATAdapter({ MoreShopView(it) })

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // TODO: Atualizar imagens.
    categoryAdapter.items = arrayListOf(
      Category(1, "https://www.ifood.com.br/static/images/categories/market.png", "Vidas Salvas: 8", 0xFFB6D048),
      Category(2, "https://www.ifood.com.br/static/images/categories/restaurant.png", "Apto para doar: 12/Dez", 0xFFE91D2D),
      Category(3, "https://www.ifood.com.br/static/images/categories/drinks.png", "Qtd doações: 2", 0xFFF6D553),
      Category(4, "https://static-images.ifood.com.br/image/upload/f_auto/webapp/landingV2/express.png", "Tipo Sanguíneo: O+", 0xFFFF0000),
    )

    bannerAdapter.items = arrayListOf(
      Banner(1, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/itensBasicosNOV21Principal_zE1X.png"),
      Banner(2, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/Bebidas40offPrincipal_cljA.png"),
      Banner(3, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/MerceariaeMatinaisPrincipal_mfDO.png"),
    )

    moreShopAdapter.items = arrayListOf(
      MoreShop(1, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/46ebd05c-116e-41cd-b3de-7a05c5bc730a/201811071958_30656.jpg", "Fundação Pró-Sangue", 4.4, "Pizza", 11.2, "Avenida Pereira Inácio, 564", 26.00),
      MoreShop(2, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/bb3ad636-7c36-4ae2-a1db-14cd35695350/202001271029_rK15_i.png", "Hemocentro da Santa Casa", 4.3, "Esfiha", 12.2, "60-70", 9.00),
      MoreShop(3, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/2fd863ac-4cc2-476c-8896-99aedfdaeb5f/201911150948_Z9QG_i.jpg", "Hemocentro HSP Unifesp", 4.9, "Grill", 17.2, "60-70", 10.00),
      MoreShop(4, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/86b58685-a7dc-4596-be26-2c4037b4d591/202006051304_JuRt_i.jpg", "Hemocentro São Lucas", 4.9, "Grill", 12.2, "70-90", 40.00),
      MoreShop(5, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/e2f3424a-06fb-46dd-89c3-f7b039e2b1f0_BOLOD_PPIN02.jpeg", "COLSAN - Posto de Coleta", 4.7, "Bolo", 11.0, "80-90", 30.00),
      MoreShop(6, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201901021647_8066dc64-9383-46d1-aa2d-56b9492e27ed.png", "Hemocentro Suzano", 4.4, "Esfiha", 11.2, "60-70", 8.00),
      MoreShop(7, "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201705131248_0ca51a98-ee95-48ac-b193-48066c8f20cc.png", "Banco de Sangue do Hospital Santa Marcelina", 4.9, "Bar", 17.2, "40-50", 13.00),
    )

    binding = FragmentRestaurantBinding.bind(view)

    binding?.let {
      it.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      it.rvCategory.adapter = categoryAdapter

      it.rvBanners.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      it.rvBanners.adapter = bannerAdapter

      it.rvShops.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      it.rvShops.adapter = shopAdapter

      it.rvMoreShops.layoutManager = LinearLayoutManager(requireContext())
      it.rvMoreShops.adapter = moreShopAdapter

      it.rvBanners.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
          if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyPositionChanged(recyclerView)
          }
        }
      })

      addDots(it.dots, bannerAdapter.items.size, 0)
    }

  }

  private fun addDots(container: LinearLayout, size: Int, position: Int) {
    container.removeAllViews()

    Array(size) {
      val textView = TextView(context).apply {
        text = getString(R.string.dotted)
        textSize = 20f
        setTextColor(
          if (position == it) ContextCompat.getColor(context, android.R.color.black)
          else ContextCompat.getColor(context, android.R.color.darker_gray)
        )
      }
      container.addView(textView)
    }
  }

  private var position: Int? = RecyclerView.NO_POSITION
  private val snapHelper = LinearSnapHelper()

  private fun notifyPositionChanged(recyclerView: RecyclerView) {
    val layoutManager = recyclerView.layoutManager
    val view = snapHelper.findSnapView(layoutManager)
    val position = if (view == null) RecyclerView.NO_POSITION else layoutManager?.getPosition(view)

    val positionChanged = this.position != position
    if (positionChanged) {
      addDots(binding!!.dots, bannerAdapter.items.size, position ?: 0)
    }
    this.position = position

  }

}



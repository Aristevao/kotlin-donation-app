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
      Category(1, "https://cdn-icons-png.flaticon.com/512/2077/2077524.png", "Vidas Salvas: 8", 0xFFB6D048),
      Category(2, "https://cdn-icons-png.flaticon.com/512/3652/3652191.png", "Apto para doar: 12/Dez", 0xFFE91D2D),
      Category(3, "https://cdn-icons-png.flaticon.com/512/1056/1056542.png", "Qtd doações: 2", 0xFFF6D553),
      Category(4, "https://cdn-icons-png.flaticon.com/512/4264/4264896.png", "Tipo Sanguíneo: O+", 0xFFFF0000),
    )

    bannerAdapter.items = arrayListOf(
      Banner(1, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/itensBasicosNOV21Principal_zE1X.png"),
      Banner(2, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/Bebidas40offPrincipal_cljA.png"),
      Banner(3, "https://static-images.ifood.com.br/image/upload/t_high/discoveries/MerceariaeMatinaisPrincipal_mfDO.png"),
    )

    moreShopAdapter.items = arrayListOf(
      MoreShop(1, "https://health.gov.tt/sites/default/files/styles/large/public/inline-images/Blood%20Bank%20logo%202022-03.png?itok=0H-a6QNb", "Fundação Pró-Sangue","Araras/SP", 8.20, "Av. Zurita, 64", "Jardin Alvorada"),
      MoreShop(3, "https://e7.pngegg.com/pngimages/833/894/png-clipart-blood-donation-blood-transfusion-organ-donation-blood-miscellaneous-logo.png", "Hemocentro HSP Unifesp",  "Araras/SP", 10.27, "R. Pedro Vargas, 98", "Copacanana"),
      MoreShop(2, "https://png.pngtree.com/png-clipart/20200701/original/pngtree-world-blood-donation-day-creative-cute-blood-drop-png-image_5357614.jpg", "Hemocentro da Santa Casa",  "Araras/SP", 14.24, "R. Maria Rosa, 12", "José Ometto"),
      MoreShop(4, "https://st2.depositphotos.com/1017986/8169/i/450/depositphotos_81693086-stock-photo-female-hands-holding-red-heart.jpg", "Hemocentro São Lucas",  "Araras/SP", 19.55, "Av. Carlos Tunes, 113", "Centro"),
      MoreShop(5, "https://banner2.cleanpng.com/20180522/yeh/kisspng-blood-donation-blood-bank-ink-vector-5b04445aafa538.1655375715270062987195.jpg", "COLSAN - Posto de Coleta",  "Rio Claro/SP", 25.0, "R. Hugo Campos, 57", "Vila Lobos"),
      MoreShop(7, "https://www.nicepng.com/png/detail/117-1179838_blood-donation-camp-blood-donation-logo-png.png", "Hospital Santa Marcelina",  "Leme/SP", 27.3, "R. Luíza Silva, 32", "Marimbondo"),
      MoreShop(6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_-s4tGFzOEA3GJGJeROiX90eHkUwrVeEiS2DMr8zVEslegACkj1wRZDujJ94Ql9hhIkY&usqp=CAU", "Hemocentro Suzano",  "Conchal/SP", 29.9, "R. Ana Ramos, 872", "Cascata"),
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



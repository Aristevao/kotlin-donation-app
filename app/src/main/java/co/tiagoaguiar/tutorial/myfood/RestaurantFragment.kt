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

    categoryAdapter.items = arrayListOf(
      Category(1, "https://cdn-icons-png.flaticon.com/512/2077/2077524.png", "Vidas Salvas: 8", 0xFFFFFF),
      Category(2, "https://cdn-icons-png.flaticon.com/512/3652/3652191.png", "Apto para doar: 12/Dez", 0xFFFFFF),
      Category(3, "https://cdn-icons-png.flaticon.com/512/1056/1056542.png", "Qtd doações: 2", 0xFFFFFF),
      Category(4, "https://cdn-icons-png.flaticon.com/512/4264/4264896.png", "Tipo Sanguíneo: O+", 0xFFFFFF),
    )

    bannerAdapter.items = arrayListOf(
      Banner(1, "https://fernandessouza.com.br/wp-content/uploads/2018/06/dia-mundial-do-doador-de-sangue-1024x1024.jpg"),
      Banner(2, "https://www.saude.ce.gov.br/wp-content/uploads/sites/9/2019/02/banner_doacao_sangue_hrsc.jpg"),
      Banner(2, "https://portalamm.org.br/wp-content/uploads/aebacd62-1e6d-463e-88f3-296a739607c6.jpeg"),
    )

    moreShopAdapter.items = arrayListOf(
      MoreShop(1, "https://health.gov.tt/sites/default/files/styles/large/public/inline-images/Blood%20Bank%20logo%202022-03.png?itok=0H-a6QNb", "ETEC Prefeito Alberto Feres","Araras/SP", 8.20, "R. César Vergueiro, 690", "Jardim Candida"),
      MoreShop(3, "https://e7.pngegg.com/pngimages/833/894/png-clipart-blood-donation-blood-transfusion-organ-donation-blood-miscellaneous-logo.png", "Hospital São Luiz",  "Araras/SP", 10.27, "R. Armando S. de Oliveira, 110", "Centro"),
      MoreShop(2, "https://png.pngtree.com/png-clipart/20200701/original/pngtree-world-blood-donation-day-creative-cute-blood-drop-png-image_5357614.jpg", "Hemocentro HSP Unifesp",  "Conchal/SP", 14.24, "Av. Nelson Cunha, 12", "Santo Antônio"),
      MoreShop(4, "https://st2.depositphotos.com/1017986/8169/i/450/depositphotos_81693086-stock-photo-female-hands-holding-red-heart.jpg", "Hemocentro São Lucas",  "Leme/SP", 19.55, "Av. Júlia Magnusson, 113", "Esperança"),
      MoreShop(5, "https://banner2.cleanpng.com/20180522/yeh/kisspng-blood-donation-blood-bank-ink-vector-5b04445aafa538.1655375715270062987195.jpg", "COLSAN - Posto de Coleta",  "Rio Claro/SP", 25.0, "R. Hugo Campos, 57", "Vila Lobos"),
      MoreShop(7, "https://www.nicepng.com/png/detail/117-1179838_blood-donation-camp-blood-donation-logo-png.png", "Hospital Santa Marcelina",  "Rio Claro/SP", 27.3, "Av. Luíza Morete, 32", "Vivaldini"),
      MoreShop(6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_-s4tGFzOEA3GJGJeROiX90eHkUwrVeEiS2DMr8zVEslegACkj1wRZDujJ94Ql9hhIkY&usqp=CAU", "Hemocentro Suzano",  "Cordeirópolis/SP", 29.9, "R. Ana Bonini, 872", "Piraporinha"),
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



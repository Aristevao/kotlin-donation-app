package co.tiagoaguiar.tutorial.myfood

import android.view.ViewGroup
import co.tiagoaguiar.atway.ui.adapter.ATViewHolder
import co.tiagoaguiar.tutorial.myfood.databinding.BloodCenterSummaryBinding
import com.squareup.picasso.Picasso

class MoreShopView(viewGroup: ViewGroup) : ATViewHolder<BloodCenter, BloodCenterSummaryBinding>(
    BloodCenterSummaryBinding::inflate,
    viewGroup
) {

    override fun bind(item: BloodCenter) {
        Picasso.get()
            .load(item.bannerUrl)
            .into(binding.imgShop)

        binding.txtShop.text = item.text
        binding.txtStar.text = itemView.context.getString(R.string.distance, item.distance)
        binding.txtSubtitle.text = itemView.context.getString(R.string.shop_category, item.city)
        binding.txtPrice.text =
            itemView.context.getString(R.string.shop_price, item.address, item.neighborhood)
    }

}
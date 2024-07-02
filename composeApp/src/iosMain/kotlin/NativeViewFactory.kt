import com.santimattius.kmp.delivery.core.domain.Vendor
import platform.UIKit.UIViewController

interface NativeViewFactory {

    fun createMapView(
        vendors: List<Vendor>,
        onItemClick: (Vendor) -> Unit
    ): UIViewController
}
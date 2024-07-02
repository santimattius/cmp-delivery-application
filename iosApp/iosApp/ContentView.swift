import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(nativeViewFactory: IOSNativeView.shared)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

class IOSNativeView : NativeViewFactory{
    static let shared = IOSNativeView()
    
    func createMapView(vendors: [Vendor], onItemClick: @escaping (Vendor) -> Void) -> UIViewController {
        let map = NativeMapView(places: vendors, onTap: onItemClick)
        return UIHostingController(rootView: map)
    }
}

struct ContentView: View {
    
    var body: some View {
        ComposeView()
    }
}



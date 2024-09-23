//
//  NativeVendorRow.swift
//  iosApp
//
//  Created by Santiago Mattiauda on 26/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct NativeVendorRow: View {
    
    let vendor: Vendor
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: vendor.logo)) { image in
                image.resizable().cornerRadius(8.0)
            } placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50)

            VStack(alignment: .leading) {
                Text(vendor.name)
                    .font(.headline)
                    .lineLimit(1)
                Text(vendor.categories.joined(separator: "*"))
                    .font(.subheadline)
                    .foregroundColor(.gray)
                HStack {
                    Text(vendor.deliveryTime)
                    Text("\(vendor.deliveryFee.formatted())")
                }
                .font(.subheadline)
                .foregroundColor(.gray)
            }
            
            Spacer()
            
            HStack(spacing: 2) {
                Image(systemName: "star.fill")
                    .foregroundColor(.orange)
                Text("\(vendor.rating.formatted())")
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
        }
        .frame(height: 100)
        .padding(.vertical, 5)
        .padding(.horizontal, 10)
    }
}


#Preview {
    NativeVendorRow(
        vendor:Vendor(
            id: 12, name: "Hello",
            logo:"https://images.deliveryhero.io/image/pedidosya/restaurants/logo-haprh3.jpg",
            location: Location_(lat: 0.0, lng: 0.0),
            isNew: false,
            isExclusive: false,
            deliveryTime: "30-45 min.",
            deliveryFee: 10,
            rating: 1,
            headerImage: "",
            categories: ["Pizza"]
        )
    )
}

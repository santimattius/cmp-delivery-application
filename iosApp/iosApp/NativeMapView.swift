//
//  NativeMapView.swift
//  iosApp
//
//  Created by Santiago Mattiauda on 26/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import MapKit
import ComposeApp

struct NativeMapView: View {
    
    var places = [Vendor]()
    var onTap:(Vendor)->Void = {_ in}
    
    @State private var region = MKCoordinateRegion(
        center: CLLocationCoordinate2D(latitude: -34.90111, longitude: -56.16453),
        span: MKCoordinateSpan(latitudeDelta: 0.05, longitudeDelta: 0.05)
    )
    var body: some View {
        Map(coordinateRegion: $region,showsUserLocation: true, annotationItems: places, annotationContent: { item in
            MapAnnotation(coordinate: CLLocationCoordinate2D(latitude: item.location.lat, longitude: item.location.lng)){
                Image(systemName: "house.circle.fill")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .foregroundColor(.red).onTapGesture {
                        onTap(item)
                    }
            }
        })
    }
}

extension Vendor:Identifiable {}

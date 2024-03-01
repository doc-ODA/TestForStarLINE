//
//  iOSApp.swift
//  iosApp
//
//  Created by doc-ODA on 28.02.2024.
//  https://github.com/doc-ODA
//

import SwiftUI
import shared

@main
struct iOSApp: App {

    init() {
        Main_iosKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
            ZStack {
                ContentView()
                    .safeAreaInset(edge: .top) {
                        Color.clear.frame(height: 75)
                    }
                    .overlay(alignment: .top) {
                        Color.clear
                            .background(.ultraThinMaterial)
                            .ignoresSafeArea(edges: .top)
                            .frame(height: 0) // 0 поднимает цвет наверх над safe area, но не вниз
                    }
            }
		}
	}
    
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

//
//  ViewController.swift
//  KotlinIOS
//
//  Created by Nathan Harary on 13/02/2020.
//  Copyright © 2020 alx. All rights reserved.
//

import UIKit

import SharedModule

class ViewController: UIViewController {

    @IBOutlet weak var lblTitle: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        lblTitle.text = CommonKt.createApplicationScreenMessage()
        
        ConversionsRepo().getConversionRates{ (result) in
            DispatchQueue.main.async {
                self.lblTitle.text = result.base
            }
        }

    }
}


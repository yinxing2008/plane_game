// 厦门大学计算机专业 | 前华为工程师
// 专注《零基础学编程系列》  http://lblbc.cn/blog
// 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
// 公众号：蓝不蓝编程
import UIKit
import SpriteKit
import CoreBluetooth

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }

    private func setupView(){
        guard let sk = view as? SKView else { return }
        sk.showsFPS = true
        sk.showsNodeCount = true
        sk.showsFields = true
        
        let scene = GameScene(size:sk.bounds.size)
        sk.presentScene(scene)
    }
}

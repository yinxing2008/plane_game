// 厦门大学计算机专业 | 前华为工程师
// 专注《零基础学编程系列》  http://lblbc.cn/blog
// 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
// 公众号：蓝不蓝编程
import UIKit
import SpriteKit

class GameOverScene: SKScene {
    
    private lazy var titleNode = SKLabelNode()
    private lazy var restartNode = SKLabelNode()
    
    override func didMove(to view: SKView) {
        let bg = SKSpriteNode.init(imageNamed: "bg")
        let subs = [bg,titleNode,restartNode]
        subs.forEach { (item) in
            addChild(item)
        }
        
        bg.anchorPoint = CGPoint.zero
        bg.size = view.bounds.size
        titleNode.text = "游戏结束"
        titleNode.position = CGPoint.init(x: view.bounds.midX, y: view.bounds.midY + 64)
        titleNode.fontColor = SKColor.systemRed
        titleNode.fontSize = CGFloat(50)
        
        restartNode.text = "重新开始"
        restartNode.name = "restart"
        restartNode.position = view.center
        restartNode.fontColor = SKColor.black
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            let point = touch.location(in: self)
            if let node = nodes(at: point).first {
                if node.name == "restart" {
                    let scene = GameScene.init(size: view!.bounds.size)
                    view?.presentScene(scene)
                }
            }
        }
    }
}

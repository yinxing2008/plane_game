// 厦门大学计算机专业 | 前华为工程师
// 专注《零基础学编程系列》  http://lblbc.cn/blog
// 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
// 公众号：蓝不蓝编程
import UIKit
import SpriteKit

class GameScene: SKScene,SKPhysicsContactDelegate{
    
    enum GameStatus {
        case playing,over//游戏状态：游戏中,游戏结束
    }

    private var myPlane: SKSpriteNode?
    private var bgNode: SKSpriteNode?
    private lazy var scoreNode = SKLabelNode()
    private lazy var images = SKTextureAtlas.init(named: "images")
    private(set) lazy var gameStatus:GameStatus = .playing
    private lazy var isMyPlaneBeenTouched = false
    private var enemyTimer: Timer?
    private var bulletTimer: Timer?
    private lazy var enemyFlag:UInt32 = 0x01 << 1
    private lazy var myPlaneFlag: UInt32 = 0x01 << 2
    private lazy var bulletFlag: UInt32 =  0x01 << 3
    private var score = 0
    
    override func didMove(to view: SKView) {
        myPlane = SKSpriteNode.init(texture: images.textureNamed("my_plane"))
        bgNode = SKSpriteNode.init(texture: images.textureNamed("bg"))
        scoreNode.fontColor = SKColor.black
        scoreNode.position = CGPoint.init(x: 100, y: self.size.height-100)
        
        let subs: [SKNode] = [bgNode!,myPlane!,scoreNode]
        subs.forEach { (node) in
            addChild(node)
        }
        layoutSubs()
    }
    
    private func layoutSubs(){
        let midx = view!.bounds.size.width / 2
        let midBottom = CGPoint.init(x: midx, y: myPlane!.size.height)
        myPlane!.position = midBottom
        let body = SKPhysicsBody.init(rectangleOf: myPlane!.size)
        body.isDynamic = false
        body.categoryBitMask = myPlaneFlag
        body.contactTestBitMask = enemyFlag
        myPlane!.physicsBody = body
        myPlane!.name = "myPlane"
        
        bgNode!.position = CGPoint.zero
        bgNode!.anchorPoint = CGPoint.zero
        bgNode!.size = view!.bounds.size

        let pword = SKPhysicsBody.init(edgeLoopFrom: view!.bounds)
        self.physicsBody = pword
        self.physicsWorld.contactDelegate = self
        
        let pan = UIPanGestureRecognizer.init(target: self, action: #selector(moveMyPlane(_:)))
        self.view!.addGestureRecognizer(pan)
        
        startGame()
    }
    
    @objc func moveMyPlane(_ pan:UIPanGestureRecognizer){
        if isMyPlaneBeenTouched {
            if let node = myPlane {
                let point = pan.location(in: self.view)
                let position = CGPoint.init(x: point.x, y: self.size.height - point.y)
                node.position = position
            }
        }
    }
    
    private func invade(){
        enemyTimer = Timer.scheduledTimer(timeInterval: 0.5, target: self, selector: #selector(createEnemy), userInfo: nil, repeats: true)
    }
    @objc func createEnemy(){
        guard gameStatus == .playing else {
            return
        }
        let top = view!.bounds.size.height
        let enemyPlane = SKSpriteNode.init(texture: images.textureNamed("enemy_plane"))
        enemyPlane.name = "enemyPlane"
        addChild(enemyPlane)
        let max = view!.bounds.size.width - enemyPlane.size.width / 2
        let min = enemyPlane.size.width / 2
        let random = arc4random() % UInt32((max - min) + min)
        
        let midTop = CGPoint.init(x: CGFloat(random), y: top - enemyPlane.size.height)
        enemyPlane.position = midTop
        let body = SKPhysicsBody.init(rectangleOf: enemyPlane.size)
        body.affectedByGravity = false
        body.categoryBitMask = enemyFlag
        body.contactTestBitMask = myPlaneFlag | bulletFlag
        enemyPlane.physicsBody = body
        let bottom = CGPoint.init(x: CGFloat(random), y: 0)
        let move = SKAction.move(to: bottom, duration: 5)
        let remove = SKAction.removeFromParent()
        enemyPlane.run(SKAction.sequence([move,remove]))
    }
    
    private func shoot(){
        bulletTimer = Timer.scheduledTimer(timeInterval: 0.25, target: self, selector: #selector(createBullet), userInfo: nil, repeats: true)
    }
    @objc func createBullet(){
        guard gameStatus == .playing else {
            return
        }
        let bullet = SKSpriteNode.init(texture: images.textureNamed("bullet"))
        bullet.name = "bullet"
        let max = view!.bounds.size.width - bullet.size.width / 2
        let min = bullet.size.width / 2
        let random = arc4random() % UInt32((max - min) + min)
        let top = CGPoint.init(x: myPlane!.position.x, y: view!.bounds.height)
        let x = myPlane!.position.x
        let y = myPlane!.position.y
        bullet.position = CGPoint.init(x: x, y: y)
        bullet.anchorPoint = CGPoint.init(x: 0.5, y: 0)
        bullet.size = CGSize.init(width: 20, height: 40)
        let move = SKAction.move(to: top, duration: 1)
        let remove = SKAction.removeFromParent()
        bullet.run(SKAction.sequence([move,remove]))
        
        let body = SKPhysicsBody.init(rectangleOf: bullet.size)
        body.categoryBitMask = bulletFlag
        body.contactTestBitMask = enemyFlag
        body.isDynamic = false
        bullet.physicsBody = body
        
        addChild(bullet)
    }
    
    //游戏结束
    private func gameOver(){
        score = 0
        gameStatus = .over
        if let feture = Calendar.current.date(byAdding: Calendar.Component.year, value: 1, to: Date()) {
            enemyTimer?.fireDate = feture
            bulletTimer?.fireDate = feture
        }
        children.forEach { (node) in
            if node.name == "bullet" ||  node.name == "enemy" {
                node.removeAllActions()
                node.removeFromParent()
            }
        }
        
        let over = GameOverScene.init(size: view!.bounds.size)
        view?.presentScene(over)
        
        clearTimer()
    }
    private func startGame(){
        let date = Date()
        if enemyTimer == nil {
            invade()
        }
        if bulletTimer == nil {
            shoot()
        }
        enemyTimer?.fireDate = date
        bulletTimer?.fireDate = date
    }
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        isMyPlaneBeenTouched = false
        if let touch = touches.first {
            let point = touch.location(in: self)
            if let node = self.nodes(at: point).first {
                if node.name == "myPlane"{
                    isMyPlaneBeenTouched = true
                }
            }
        }
    }
    
    //碰撞检测
    func didBegin(_ contact: SKPhysicsContact) {
        let flag = contact.bodyA.categoryBitMask | contact.bodyB.categoryBitMask
        switch flag {
        case myPlaneFlag | enemyFlag:
            gameOver()
        case bulletFlag | enemyFlag:
            clearNode(contact)
            updateScore()
        default:
            break
        }
    }
    
    private func updateScore(){
        score += 1
        scoreNode.text = String("得分: \(score)")
    }
    func didEnd(_ contact: SKPhysicsContact) {
        
    }
    private func clearNode(_ contact: SKPhysicsContact){
        let nodeA = contact.bodyA.node
        let nodeB = contact.bodyB.node
        nodeA?.removeAllActions()
        nodeA?.removeFromParent()
        nodeB?.removeAllActions()
        nodeB?.removeFromParent()
        
        var textures = Array<SKTexture>()
        for i in 1 ... 19 {
            let texture = images.textureNamed("explosion\(i)")
            textures.append(texture)
        }
        let node = SKSpriteNode.init(texture: textures[0])
        node.position = nodeA!.position
        addChild(node)
        let anim = SKAction.animate(with: textures, timePerFrame: 0.01)
        let remove = SKAction.removeFromParent()
        let actions = SKAction.sequence([anim,remove,SKAction.run {
            node.removeAllActions()
            }])
        node.run(actions)
    }

    private func clearTimer(){
        bulletTimer?.invalidate()
        bulletTimer = nil
        enemyTimer?.invalidate()
        enemyTimer = nil
    }
}

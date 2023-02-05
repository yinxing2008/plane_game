# 厦门大学计算机专业 | 前华为工程师
# 专注《零基础学编程系列》  http: // lblbc.cn / blog
# 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
# 公众号：蓝不蓝编程
import pygame


class Bullet(pygame.sprite.Sprite):
    def __init__(self, position):
        pygame.sprite.Sprite.__init__(self)
        
        self.image = pygame.image.load("images/bullet.png").convert_alpha()
        self.rect = self.image.get_rect()
        self.rect.left, self.rect.top = position
        self.speed = 12
        self.active = False
        self.mask = pygame.mask.from_surface(self.image)
    
    def move(self):
        self.rect.top -= self.speed
        
        if self.rect.top < 0:
            self.active = False
    
    def reset(self, position):
        self.rect.left, self.rect.top = position
        self.active = True
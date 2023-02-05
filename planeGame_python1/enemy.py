import pygame
from random import *


class EnemyPlane(pygame.sprite.Sprite):
    def __init__(self, bg_size):
        pygame.sprite.Sprite.__init__(self)
        
        self.image = pygame.image.load("images/enemy_plane.png").convert_alpha()
        self.destroy_images = []
        self.destroy_images.extend([
            pygame.image.load("images/enemy_down1.png").convert_alpha(),
            pygame.image.load("images/enemy_down2.png").convert_alpha(),
            pygame.image.load("images/enemy_down3.png").convert_alpha(),
            pygame.image.load("images/enemy_down4.png").convert_alpha()
        ])
        self.rect = self.image.get_rect()
        self.width, self.height = bg_size[0], bg_size[1]
        self.speed = 2
        self.active = True
        self.rect.left, self.rect.top = \
            randint(0, self.width - self.rect.width), \
            randint(-5 * self.height, 0)
        self.mask = pygame.mask.from_surface(self.image)
    
    def move(self):
        if self.rect.top < self.height:
            self.rect.top += self.speed
        else:
            self.reset()
    
    def reset(self):
        self.active = True
        self.rect.left, self.rect.top = \
            randint(0, self.width - self.rect.width), \
            randint(-5 * self.height, 0)

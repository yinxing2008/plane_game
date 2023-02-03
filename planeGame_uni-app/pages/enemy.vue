//厦门大学计算机专业 | 前华为工程师
//专注《零基础学编程系列》  http://lblbc.cn/blog
//包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
//公众号：蓝不蓝编程
<template>
	<view :class="getEnemyClass" :style="{ left: data.x + 'px', top: data.y + 'px' }"></view>
</template>

<script>
import { isCollision } from '../utils/utils.js';
export default {
	props: {
		data: {
			type: Object,
			default: () => {
				return {};
			}
		},
		bulletData: {
			type: Array,
			default: () => []
		},
		params: {
			type: Object,
			default: () => {
				return {};
			}
		}
	},
	data() {
		return {
			x: 0,
			y: 0,
			enemyY: 1,
			moveTimer: null
		};
	},
	computed: {
		getEnemyClass() {
			const classStyle = [`enemy`];
			const explosion = {};
			explosion[`enemy_effect`] = this.data.isExplosion;
			classStyle.push(explosion);
			return classStyle;
		}
	},
	methods: {
		move() {
			if (this.data.y < this.data.screenHeight) {
				this.data.y += this.enemyY;
			} else {
				this.remove();
			}
		},
		remove() {
			this.$emit('remove', this.data.id);
		},
		init() {
			this.moveTimer = () => {
				//敌机移动
				this.move();

				// 碰撞检测
				this.detectCollision();

				// 重绘，无限循环
				requestAnimationFrame(this.moveTimer);
			};
			this.moveTimer();
		},
		detectCollision() {
			if (this.bulletData.length === 0) return;

			this.bulletData.forEach(el => {
				// console.log(el.x, el.y, this.data.x, this.data.y);
				const collision = isCollision(el, this.data);
				if (collision) {
					new Promise(resolve => {
						this.data.isExplosion = true;

						// 删除子弹
						this.$emit('removeBullet', el.id);

						setTimeout(() => resolve(), 450);
					}).then(res => {
						this.remove();
					});
				}
			});
		}
	},
	mounted() {
		this.init();
	}
};
</script>

<style scoped>
.enemy {
	width: 59px;
	height: 36px;
	position: fixed;
	z-index: 1;
	background: url(@/static/images/enemy_plane.png) no-repeat left top;
}

.enemy_effect {
	animation: enemy_animate 0.5s steps(5) both infinite;
	-webkit-animation: enemy_animate 0.5s steps(5) both infinite;
}
/* 敌机-爆炸效果 */
@keyframes enemy_animate {
	0% {
		background-position: 0 0;
	}

	100% {
		background-position: -295px 0;
	}
}

@-webkit-keyframes enemy_animate {
	0% {
		background-position: 0 0;
	}

	100% {
		background-position: -295px 0;
	}
}
</style>

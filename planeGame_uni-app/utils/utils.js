export function deleteModel(array, id) {
	let index = 0;
	for (let i = 0; i < array.length; i++) {
		if (array[i].id === id) {
			index = i;
			break;
		}
	}
	array.splice(index, 1);
}

export function isCollision(target, enemy) {
	return !(
		target.x + target.width < enemy.x ||
		enemy.x + enemy.width < target.x ||
		target.y + target.height < enemy.y ||
		enemy.y + enemy.height < target.y
	);
}

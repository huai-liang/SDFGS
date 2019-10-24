/**
 * 日期格式化函数，注意这时候传过来的是国际标准的时间格式
 * 格式化只保留yyyy-MM-dd hh:mm:ss
 */
export function dateTimeFormat(time) {
	if (undefined == time) return;
	var date = new Date(time);
	var year = date.getFullYear();
  /* 在日期格式中，月份是从0开始的，因此要加0
   * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
   * */
	var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
	var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
	// 拼接
	return year + "-" + month + "-" + day + ' ' + hour + ':' + minutes + ':' + seconds;
};

/**
 * 日期格式化函数，注意这时候传过来的是国际标准的时间格式
 * 格式化只保留yyyy-MM-dd
 */
export function dateFormat(time) {
	if (undefined == time) return;
	var date = new Date(time);
	var year = date.getFullYear();
  /* 在日期格式中，月份是从0开始的，因此要加0
   * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
   * */
	var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
	var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	// 拼接
	return year + "-" + month + "-" + day;
};

/**
 * 日期格式化函数，注意这时候传过来的是yyyy-MM-dd hh:mm:ss的格式的日期
 * 格式化只保留yyyy-MM-dd
 * 2019-04-23 18:00:00  => 2019-04-23
 * @param {String} value
 */
export function dateSubstr(value) {
	var newDate = value;
	if (null != value) {
		newDate = newDate.substr(0, 10);
	}
	return newDate;
};

/**
 * 10000 => "10,000"
 * @param {number} num
 */
export function toThousandFilter(num) {
	return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','))
};

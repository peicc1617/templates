class ImproveRadar {
    constructor(data, target) {
        this.data = {
            fieldNames:[],
            weights:[],
            values:[[]]
        }
        data.forEach(d=>{
            this.data.fieldNames.push(d.indexID);
            this.data.weights.push(d.w);
            this.data.values[0].push(d.res);
        });
        this.width =   $(target).width();
        this.height =  $(target).height();

        this.radius = this.height/3;
        this.total = data.length;
        this.level = 4;
        this.rangeMin = 0;
        this.rangeMax =1;
        this.arc = 2*Math.PI;
        this.main = d3.select(target).append('g').classed('main', true)
                .attr('transform', `translate(${this.width/2},${this.height/2})`);
    }

    init(option) {
        //计算每个指标所在的角度
        const wSum = eval(this.data.weights.join('+'))
        this.onePieces = this.data.weights.map(w => w / wSum * 2 * Math.PI)
        for (let i = 1; i < this.onePieces.length; i++) {
            this.onePieces[i] += this.onePieces[i - 1];
        }

        //计算网轴的正多边形的坐标
        this.polygons = {
            webs: [],
            webPoints: []
        }

        for (let k = this.level; k > 0; k--) {
            let webs = '',
                webPoints = [];
            let r = this.radius / this.level * k;
            for (let i = 0; i < this.total; i++) {
                let x = r * Math.sin(this.onePieces[i]),
                    y = r * Math.cos(this.onePieces[i]);
                webs += x + ',' + y + ' ';
                webPoints.push({
                    x: x,
                    y: y
                });
            }
            this.polygons.webs.push(webs);
            this.polygons.webPoints.push(webPoints);
        }

        //计算雷达图图标的坐标
        this.areasData = [];
        const values = this.data.values;
        for (let i = 0; i < values.length; i++) {
            var value = values[i],
                area = '',
                points = [];
            for (let k = 0; k < this.total; k++) {
                let r = this.radius * (value[k] - this.rangeMin) / (this.rangeMax - this.rangeMin);
                let x = r * Math.sin(this.onePieces[k]),
                    y = r * Math.cos(this.onePieces[k]);
                area += x + ',' + y + ' ';
                points.push({
                    x: x,
                    y: y
                })
            }
            this.areasData.push({
                polygon: area,
                points: points
            });
        }

        // 计算文字标签坐标
        this.textPoints = [];
        const textRadius = this.radius+10 ;
        for (let i = 0; i < this.total; i++) {
            const x = textRadius * Math.sin(this.onePieces[i]),
                y = textRadius * Math.cos(this.onePieces[i]);
            this.textPoints.push({
                x: x,
                y: y
            });
        }

        this.drawBg();
        this.drawWebs();
        this.drawLines();
        this.drawArea();
        this.drawText();
    }

    /**
     * 绘制背景圆
     */
    drawBg() {
        const bg = this.main.append('g').classed('bg', true);
        bg.selectAll('circle')
            .data([this.radius])
            .enter()
            .append('circle')
            .attr('r', d => d)
            .attr('cx', 0)
            .attr('cy', 0)
            .attr('fill-opacity', 0)
            .attr('stroke-dasharray', '5,5')
            .attr('stroke', 'black');
    }

    /**
     * 绘制多边形网格
     */
    drawWebs() {
        const webs = this.main.append('g').classed('webs', true);
        webs.selectAll('polygon')
            .data(this.polygons.webs)
            .enter()
            .append('polygon')
            .attr('points', d=>d);

    }

    /**
     * 绘制纵轴
     */
    drawLines(){
        const lines = this.main.append('g').classed('lines', true);
        lines.selectAll('line')
            .data(this.polygons.webPoints[0])
            .enter()
            .append('line')
            .attr('x1', 0)
            .attr('y1', 0)
            .attr('x2', function (d) {
                return d.x;
            })
            .attr('y2', function (d) {
                return d.y;
            });
    }

    drawArea(){
        // 添加g分组包含所有雷达图区域
        const areas = this.main.append('g').classed('areas', true);
        // 添加g分组用来包含一个雷达图区域下的多边形以及圆点
        areas.selectAll('g')
            .data(this.areasData)
            .enter()
            .append('g')
            .attr('class', (d,i)=>'area' + (i + 1));

        for (let i = 0; i < this.areasData.length; i++) {
            // 依次循环每个雷达图区域
            const area = areas.select('.area' + (i + 1)),
                areaData = this.areasData[i];
            // 绘制雷达图区域下的多边形
            area.append('polygon')
                .attr('points', areaData.polygon)
                .attr('stroke', getColor(i))
                .attr('fill', getColor(i));
            // 绘制雷达图区域下的点
            const circles = area.append('g')
                .classed('circles', true);
            circles.selectAll('circle')
                .data(areaData.points)
                .enter()
                .append('circle')
                .attr('cx', d=>d.x)
                .attr('cy', d=>d.y)
                .attr('r', 3)
                .attr('stroke',getColor(i));
        }
    }

    /**
     * 绘制文字标签
     */
    drawText(){
        const radar =this;
        const texts = this.main.append('g')
            .classed('texts', true);
        texts.selectAll('text')
            .data(this.textPoints)
            .enter()
            .append('text')
            .attr('x', d=>d.x)
            .attr('y', d=>d.y)
            .attr('text-anchor',d=>{
                let r = Math.abs(d.y/d.x);
                if(r>3){

                    return "middle"
                }
                if(d.x>1){
                    return "start";
                }else if(d.x<1){
                    return "end";
                }
            })
            .attr("dominant-baseline",d=>{
                if(d.y<0){
                    return "ideographic";
                }
                if(d.y>0){
                    return "hanging"
                }
            })
            .attr("font-size",12)
            .text((d,i)=>{
                return radar.data.fieldNames[i];
            });
    }



}
function getColor(idx) {
    const palette = [
        '#2ec7c9', '#b6a2de', '#5ab1ef', '#ffb980', '#d87a80',
        '#8d98b3', '#e5cf0d', '#97b552', '#95706d', '#dc69aa',
        '#07a2a4', '#9a7fd1', '#588dd5', '#f5994e', '#c05050',
        '#59678c', '#c9ab00', '#7eb00a', '#6f5553', '#c14089'
    ]
    return palette[idx % palette.length];
}
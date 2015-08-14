/**
 * @param id 			id of the div for destroying
 */
function destroyChart(id){
	$('#'+id).highcharts().destroy();
}
/**
 * @param id			id of div for chart show
 * @param title			title for chart
 * @param subtitle		subtitle under title
 * @param ytitle		title for vertical
 * @param categories	horizontal categories
 * @param unit			unit display following with integer
 * @param series		data collection. format[{name:'*',data[*,*,*...]},{}..]
 */
function columnInit(id, title, subtitle, ytitle, categories, unit, series){
				$('#'+id).highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: title
            },
            subtitle: {
                text: subtitle
            },
            xAxis: {
                categories: categories
            },
            yAxis: {
                min: 0,
                title: {
                    text: ytitle
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} '+unit+'</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: series
        });
}

/**
 * @param id			id of div for chart show
 * @param title			title for chart
 * @param subtitle		subtitle under title
 * @param ytitle		title for vertical
 * @param categories	horizontal categories
 * @param unit			unit display following with integer
 * @param series		data collection. format[{name:'*',data[*,*,*...]},{}..]
 */
function lineInit(id, title, subtitle, ytitle, categories, unit, series){
	$('#'+id).highcharts({
    chart: {
        type: 'line',
        marginRight: 130,
        marginBottom: 25
    },
    title: {
        text: title,
        x: -20 //center
    },
    subtitle: {
        text: subtitle,
        x: -20
    },
    xAxis: {
        categories: categories
    },
    yAxis: {
        title: {
            text: ytitle
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
		headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} '+unit+'</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: -10,
        y: 100,
        borderWidth: 0
    },
    series: series
});
}
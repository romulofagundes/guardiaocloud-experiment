$(function () {
    $.getJSON( "js/result_exec3.json", function( data ) {
      $('#container3').highcharts(defaults.sucesso(data,"3"));
      $('#container3-erro').highcharts(defaults.falha(data,"3"));
      $('#container3-count').highcharts(defaults.quantidade(data,"3"));
    });
});

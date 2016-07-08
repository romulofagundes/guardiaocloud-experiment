$(function () {
    $.getJSON( "js/result_exec2.json", function( data ) {
      $('#container2').highcharts(defaults.sucesso(data,"2"));
      $('#container2-erro').highcharts(defaults.falha(data,"2"));
      $('#container2-count').highcharts(defaults.quantidade(data,"2"));
    });
});

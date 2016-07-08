$(function () {
  $.getJSON( "js/result_exec2.json", function( data1 ) {
      $.getJSON( "js/result_exec1.json", function( data2 ) {
        $('#container-1-2').highcharts(defaults.join(data1,data2,"1 e 2"));
      });
    });
});

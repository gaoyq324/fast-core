$(function () {
    $.ajax({
        type: "POST",
        url: "/sap-api/rest/list",
        contentType: "application/json",
        success: function (r) {
            if (r.code === 0) {
                obj = r.result;
                result = '';
                columns ="说明,URL,METHOD,入参,出参,服务名,实例名,入参名,出参名".split(",");
                columnKeys ="desc,url,method,inputParamDto,returnParamDto,serviceName,beanName,inputParam,returnParam".split(",");
                result_header = '<tr>';
                td_head ='';
                // for(i=0;i<columns.length;i++){
                //     td_head +=  '<th>'+ columns[i]+'</th>';
                // }

                result_header += td_head+'</tr>'
                $('#testTable').append(result_header);

                for(var index in obj){
                    //str = '';
                    result += '<tr>';
                    td ='';
                    for(i=0;i<columns.length;i++){
                        td +=  '<td style="word-wrap:break-word;">'+ obj[index][columnKeys[i]]+'</td>';
                    }

                    result +=  td+'</tr>'

                }
                $('#testTable').append(result);
            } else {
                alert(r.msg);
            }
        }
    });

})

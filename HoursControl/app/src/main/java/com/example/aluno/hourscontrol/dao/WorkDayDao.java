package com.example.aluno.hourscontrol.dao;

import android.content.Context;
import android.util.Log;

import com.example.aluno.hourscontrol.database.Database;
import com.example.aluno.hourscontrol.model.WorkDay;
import com.example.aluno.hourscontrol.util.Session;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.list;

//Classe DAO, que conrola a conexão do banco com os elementos do WorkDay
public class WorkDayDao {


    // adc no banco um WorkDay no banco
    // context = activity que chama a função
    // day = workDay que será adc no banco.
    public void addWorkDay(Context context, WorkDay day){
        // instancia o banco
        Database database = new Database(context);
        // inseri no objeto day o usuario que está logado na Session
        day.setUserLoggin(Session.getUser());
        // adc no banco através da insertWorkDay.
        // retorna o id do workDay adc no sistema
        long id = database.insertWorkDay(day);
    }

    // função que calcula o balanço do dia.
    public Date calcBalanceOfTheDay (WorkDay day) {

        /**     Vamos a um exemplo. Quando você pega um .getTime() em uma variavel do tipo Date,
         * ele passa um long com os milisegundos daquela data. então o que nós fazemos pra
         * calcular, transformamos essas datas em long pela função .getTime() e fazemos os
         * calculos e depois as transformamos novamente em tipo Date.
         *      Porém quando vc realiza a subtração dos .getTime() de duas datas uma coisa que ele
         * subrai é a contagem da data, como usamos datas do mesmo dia o tempo das datas é zerado
         * como se estivesse ( 00/00/00 ) ou seja não existe nada, apenas o tempo da horas que
         * subtraindo, afinal as horas são diferentes.
         */

        // timeMorning = data de saida pro inveralo - a data de entrada na empresa
        // timeAfternoon = data de saida da empresa - a data de volta do intervalo
        long timeMorning = day.getDateBreak().getTime() - day.getDateEntry().getTime();
        long timeAfternoon = day.getDateOut().getTime() - day.getDatebackBreak().getTime();

        // timeDay = a soma do horário da tarde + o horário da manha.
        long timeDay = timeAfternoon + timeMorning;
        // cria um novo Date chamado BalanceDay
        Date balanceDay = new Date();

        /**     Vamos a outro exemplo. Quando vc cria um novo date, essa função e iniciada com uma
         * data padrão do java, (exemplo: 10/09/1970 - 21:00:00) ou seja já vem com coisa dele,
         * então como vamos usar as horas pra calculos e afins, precisamos zerar todas ela. para que
         * o Date tenha como padrão a meia noite ( 00:00:00 ) para que quando formos inserir os dados
         * da hora nela não tenha nenhum erro.
         */

        // zerando os horarios do balanceDay
        balanceDay.setHours(0);
        balanceDay.setMinutes(0);
        balanceDay.setSeconds(0);

        /** adicionando ao timeDay, o valor dele mais o .getTime do balanceDay, ou seja estamos
         * pegando e adicionando as horas calculadas pela gente no timeDay, com a data do balanceDay
         * pq afinal a gente pegou e zerou as horas do balanceDay , e zerou a a data do timeDay.
         * então estamos apenas colocando as horas do timeDay na data do balance Day.
         */

        timeDay = timeDay + balanceDay.getTime();

        /**     Agora vamos pegar o timeDay e tranforma-lo em um Date, afinal até agora ele é um long
         *  pra isso vamos usar a propria balanceDay, que já criamos e instanciamos. então se fizermos
         *  um .setTime() no balanceDay, vamos jogar o tempo que calculamos na variavel Date setando ela
         *  com a data que calculamos
         */
        balanceDay.setTime(timeDay);

        // retorna o balance Day
        return balanceDay;
    }

    // lista que retorna todos os workDays cadastrados no sistema
    // essa função não é usada no projeto.
    public List<WorkDay> returnWorkDays(Context context) throws ParseException {
        // instancia o banco
        Database database = new Database(context);
        // retorna os WorkDays através da função do banco que retorna WorkDays,
        // return a lista
        return database.returnWorkDays();
    }
    // função que retorna os WorkDays pelo usuario que está logado
    // context = activity que chama a função
    // idUser = id do usuario que está logado
    // month mês que está selecionado no spinner da lista
    public List<WorkDay> returnWorkDaysByUser(Context context, int idUser, int month) throws ParseException {
        // instancia o banco
        Database database = new Database(context);
        // cria uma nova lista que workDay
        List<WorkDay> daysByUser = new ArrayList<WorkDay>();
        //for que percorre a lista que é retornada pelo banco de workDays
        for(WorkDay d : database.returnWorkDays()){
            // if que verifica se o dia que foi retornado do banco pertence ao usuario logado
            // verifica via id
            if(d.getUserLoggin().getId() == idUser){
                //Verifica se existe algum mes selecionado no spinner,
                if(month == (-1)) {
                    // se não tiver ele vai adicionando todos na lista de retorno
                    daysByUser.add(d);
                }
                else{// senão
                    // ele verifica se o mes da data de entrada é igual ao mes selecionado
                    if(d.getDateEntry().getMonth() == month){
                        // se for ele inseri na lista.
                        daysByUser.add(d);
                    }
                }
            }
        }
        // retorna os dias selecionados
        return daysByUser;
    }
}

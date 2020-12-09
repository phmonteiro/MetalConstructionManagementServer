package ejbs;

import entities.Variante;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton(name = "ConfigBean")
@Startup
public class ConfigBean {

    private static final Logger LOGGER = Logger.getLogger("ebjs.ConfigBean");

    @EJB
    ProjectBean projectBean;
    @EJB
    ClientBean clientBean;
    @EJB
    DesignerBean designerBean;
    @EJB
    FamilyProductBean familyProductBean;
    @EJB
    TypeProductBean typeProductBean;
    @EJB
    ProductBean productBean;
    @EJB
    StructureBean structureBean;
    @EJB
    ManufacturerBean manufacturerBean;
    @EJB
    VarianteBean varianteBean;
    @EJB
    SimulacaoBean simulacaoBean;

    @PostConstruct
    void populateDB() {

        try {
//            clientBean.create("Jose", "123", "jose@mail.pt", "123456", "asdasfgafdg");
//            designerBean.create("Joao", "123", "joao@mail.pt");
            manufacturerBean.create("XXX", "123", "zxcx@asd.com");
//            projectBean.create("JoseProjeto", 1L, 2L);
//            structureBean.create("ASDasd", "JoseProjeto");
            typeProductBean.create("Perfis Enformados a Frio");
//            typeProductBean.create("Chapa Perfilada");
//            typeProductBean.create("Laje Mista");
//            typeProductBean.create("Painel Sandwich");
            familyProductBean.create("Superomega", "Perfis Enformados a Frio");
            familyProductBean.create("Madre C", "Perfis Enformados a Frio");
            familyProductBean.create("Madre Z", "Perfis Enformados a Frio");
//            familyProductBean.create("P0-272-30", "Chapa Perfilada");
            productBean.create("Product", "Superomega", 1L);
//            structureBean.productOnStru("Product", "ASDasd");
            System.out.println("####### A criar produtos...");
            productBean.create("Section C 220 BF", "Madre C", 1L);
            productBean.create("Section Z 220 BF", "Madre Z", 1L);
            System.out.println("####### A criar variantes...");

            //PODE LER-SE OS VALORES DOS PRODUTOS/VARIANTES DE EXCELS OU CSVs (ver excels fornecidos)
            //Exemplo básico de adição de variantes "à mão"
            varianteBean.create(1, "Section C 220 BF", "C 120/50/21 x 1.5", 13846, 13846, 375, 220000);
            varianteBean.create(2, "Section C 220 BF", "C 120/60/13 x 2.0", 18738, 18738, 500, 220000);

            //PODE LER-SE OS VALORES mcr_p E mcr_n A PARTIR DE UM EXCEL OU CSV (ver excels fornecidos para os produtos Perfil C e Z, que têm os valores mcr)
            //Exemplo básico de adição de valores mcr "à mão"
            Variante variante1 = varianteBean.getVariante(1);
            variante1.addMcr_p(3.0, 243.2123113);
            variante1.addMcr_p(4.0, 145.238784);
            variante1.addMcr_p(5.0, 99.15039028);
            variante1.addMcr_p(6.0, 73.71351699);
            variante1.addMcr_p(7.0, 58.07716688);
            variante1.addMcr_p(8.0, 47.68885195);
            variante1.addMcr_p(9.0, 40.37070843);
            variante1.addMcr_p(10.0, 34.9747033);
            variante1.addMcr_p(11.0, 30.84866055);
            variante1.addMcr_p(12.0, 27.59984422);

            //Válido para variantes simétricas, em que os mcr_p são iguais aos mcr_n
            variante1.setMcr_n((LinkedHashMap<Double, Double>) variante1.getMcr_p().clone());

            Variante variante2 = varianteBean.getVariante(2);
            variante2.addMcr_p(3.0, 393.1408237);
            variante2.addMcr_p(4.0, 241.9157907);
            variante2.addMcr_p(5.0, 169.7815504);
            variante2.addMcr_p(6.0, 129.3561949);
            variante2.addMcr_p(7.0, 104.0782202);
            variante2.addMcr_p(8.0, 86.9803928);
            variante2.addMcr_p(9.0, 74.71876195);
            variante2.addMcr_p(10.0, 65.52224563);
            variante2.addMcr_p(11.0, 58.37786338);
            variante2.addMcr_p(12.0, 52.65428332);

            //Válido para variantes de geometria simétrica, em que os mcr_p são iguais aos mcr_n
            variante2.setMcr_n((LinkedHashMap<Double, Double>) variante2.getMcr_p().clone());


            System.out.println("####### FINISHED!!");

            //EXEMPLO DA SIMULAÇÃO PARA DUAS VARIANTES DO PERFIL C, E PARA UMA ESTRUTURA DE 3 vãos (nb) de 3m cada (LVao) E SOBRECARGA 500000 (q)
            if (simulacaoBean.simulaVariante(3, 3.0, 500000, variante1)) {
                System.out.println(variante1.getNome() + " pode ser usada.");
            } else {
                System.out.println(variante1.getNome() + " não pode ser usada.");
            }

            if (simulacaoBean.simulaVariante(3, 3.0, 500000, variante2)) {
                System.out.println("A variante " + variante2.getNome() + " pode ser usada.");
            } else {
                System.out.println("A variante " + variante2.getNome() + " não pode ser usada.");
            }
        } catch (Exception exception){
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }
}

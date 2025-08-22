import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connect() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/imobiliaria",
            "root",
            "senha"
        );
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu Imobiliária ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Imóvel");
            System.out.println("3. Listar Imóveis Disponíveis");
            System.out.println("4. Listar Contratos Ativos");
            System.out.println("5. Clientes com mais contratos");
            System.out.println("6. Contratos expirando nos próximos 30 dias");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            try (Connection conn = connect()) {
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("CPF: ");
                        String cpf = sc.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();

                        PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO Cliente (nome, cpf, telefone) VALUES (?, ?, ?)"
                        );
                        ps.setString(1, nome);
                        ps.setString(2, cpf);
                        ps.setString(3, telefone);
                        ps.executeUpdate();

                        System.out.println("Cliente cadastrado!");
                        break;

                    case 2:
                        System.out.print("Endereço: ");
                        String end = sc.nextLine();
                        System.out.print("Tipo: ");
                        String tipo = sc.nextLine();
                        System.out.print("Valor aluguel: ");
                        double valor = sc.nextDouble();

                        PreparedStatement ps2 = conn.prepareStatement(
                            "INSERT INTO Imovel (endereco, tipo, valor_aluguel) VALUES (?, ?, ?)"
                        );
                        ps2.setString(1, end);
                        ps2.setString(2, tipo);
                        ps2.setDouble(3, valor);
                        ps2.executeUpdate();

                        System.out.println("Imóvel cadastrado!");
                        break;

                    case 3:
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(
                            "SELECT * FROM Imovel WHERE status = 'disponivel'"
                        );
                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("id_imovel") + " - " +
                                rs.getString("endereco") + " - R$" +
                                rs.getDouble("valor_aluguel")
                            );
                        }
                        break;

                    case 4:
                        Statement st2 = conn.createStatement();
                        ResultSet rs2 = st2.executeQuery(
                            "SELECT * FROM Contrato WHERE status = 'ativo'"
                        );
                        while (rs2.next()) {
                            System.out.println(
                                "Contrato " + rs2.getInt("id_contrato") +
                                " | Cliente " + rs2.getInt("id_cliente") +
                                " | Imóvel " + rs2.getInt("id_imovel")
                            );
                        }
                        break;

                    case 5:
                        Statement st3 = conn.createStatement();
                        ResultSet rs3 = st3.executeQuery(
                            "SELECT c.id_cliente, c.nome, COUNT(co.id_contrato) AS total_contratos " +
                            "FROM Cliente c " +
                            "JOIN Contrato co ON c.id_cliente = co.id_cliente " +
                            "GROUP BY c.id_cliente, c.nome " +
                            "ORDER BY total_contratos DESC"
                        );
                        while (rs3.next()) {
                            System.out.println(
                                rs3.getInt("id_cliente") + " - " +
                                rs3.getString("nome") + " | Contratos: " +
                                rs3.getInt("total_contratos")
                            );
                        }
                        break;

                    case 6:
                        Statement st4 = conn.createStatement();
                        ResultSet rs4 = st4.executeQuery(
                            "SELECT id_contrato, id_cliente, id_imovel, data_fim " +
                            "FROM Contrato " +
                            "WHERE data_fim BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) " +
                            "AND status = 'ativo'"
                        );
                        while (rs4.next()) {
                            System.out.println(
                                "Contrato " + rs4.getInt("id_contrato") +
                                " | Cliente: " + rs4.getInt("id_cliente") +
                                " | Imóvel: " + rs4.getInt("id_imovel") +
                                " | Vencimento: " + rs4.getDate("data_fim")
                            );
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (opcao != 0);

        sc.close();
    }
}

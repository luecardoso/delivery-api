package com.deliverytech.delivery.config;

import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.enums.Role;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.exceptions.BusinessException;
import com.deliverytech.delivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO CARGA DE DADOS DE TESTE ===");

        // Limpar dados existentes
        pedidoRepository.deleteAll();
        produtoRepository.deleteAll();
        restauranteRepository.deleteAll();
        clienteRepository.deleteAll();

        // Inserir dados de teste
        inserirUsuarios();
        inserirClientes();
        inserirRestaurantes();
        inserirProdutos();
        inserirPedidos();

        // Executar testes das consultas
        testarConsultas();

        System.out.println("=== CARGA DE DADOS CONCLUÍDA ===");
    }

    private void inserirClientes() {
        // Inserindo Clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("(11) 99999-1111");
        cliente1.setEndereco("Rua A, 123 - São Paulo/SP");
        cliente1.setDataCadastro(LocalDateTime.now());
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("(11) 99999-2222");
        cliente2.setEndereco("Rua B, 456 - São Paulo/S");
        cliente2.setDataCadastro(LocalDateTime.now());
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro Oliveira");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("(11) 99999-3333");
        cliente3.setEndereco("Rua C, 789 - São Paulo/SP");
        cliente3.setDataCadastro(LocalDateTime.now());
        cliente3.setAtivo(true);

        Cliente cliente4 = new Cliente();
        cliente4.setNome("Simone Lara Rocha");
        cliente4.setEmail("simone.lara.rocha@fanger.com.br");
        cliente4.setTelefone("(67) 93754-5597");
        cliente4.setEndereco("Avenida Clodoaldo Garcia");
        cliente4.setDataCadastro(LocalDateTime.now());
        cliente4.setAtivo(true);

        Cliente cliente5 = new Cliente();
        cliente5.setNome("Emilly Andreia Marina Fogaça");
        cliente5.setEmail("emilly.andreia.fogaca@nonesiglio.com.br");
        cliente5.setTelefone("(95) 99917-0433");
        cliente5.setEndereco("Rua Curimatã");
        cliente5.setDataCadastro(LocalDateTime.now());
        cliente5.setAtivo(true);

        Cliente cliente6 = new Cliente();
        cliente6.setNome("Heitor Juan Diego Lopes");
        cliente6.setEmail("heitor.juan.lopes@live.com.pt");
        cliente6.setTelefone("(92) 99470-0653");
        cliente6.setEndereco("Rua Tupaiú");
        cliente6.setDataCadastro(LocalDateTime.now());
        cliente6.setAtivo(true);

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3, cliente4,cliente5,cliente6));
    }

    private void inserirRestaurantes() {
        //Inserindo Restaurantes
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Pizzaria Bella");
        restaurante1.setCategoria("Italiana");
        restaurante1.setEndereco("Av. Paulista, 1000 - São Paulo/SP");
        restaurante1.setTelefone("((11) 3333-1111");
        restaurante1.setTaxaEntrega(new BigDecimal("5.0"));
        restaurante1.setAvaliacao(new BigDecimal("4.5"));
        restaurante1.setAtivo(true);
        restaurante1.setDataCriacao(LocalDateTime.now());

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Burger House");
        restaurante2.setCategoria("Hamburgueria");
        restaurante2.setEndereco("Rua Augusta, 500 - São Paulo/SP");
        restaurante2.setTelefone("(11) 3333-2222");
        restaurante2.setTaxaEntrega(new BigDecimal("3.50"));
        restaurante2.setAvaliacao(new BigDecimal("4.2"));
        restaurante2.setAtivo(true);
        restaurante2.setDataCriacao(LocalDateTime.now());

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNome("Sushi Master");
        restaurante3.setCategoria("Japonesa");
        restaurante3.setEndereco("Rua Liberdade, 200 - São Paulo/SP");
        restaurante3.setTelefone("(11) 3333-3333");
        restaurante3.setTaxaEntrega(new BigDecimal("8.0"));
        restaurante3.setAvaliacao(new BigDecimal("4.8"));
        restaurante3.setAtivo(true);
        restaurante3.setDataCriacao(LocalDateTime.now());

        Restaurante restaurante4 = new Restaurante();
        restaurante4.setNome("Hamburgueria do Penguim");
        restaurante4.setCategoria("Hamburgueria");
        restaurante4.setEndereco("Rua Rio São Francisco");
        restaurante4.setTelefone("(92) 98111-3224");
        restaurante4.setTaxaEntrega(new BigDecimal("3.50"));
        restaurante4.setAvaliacao(new BigDecimal("4.0"));
        restaurante4.setAtivo(true);
        restaurante4.setDataCriacao(LocalDateTime.now());

        Restaurante restaurante5 = new Restaurante();
        restaurante5.setNome("Ristorante Fioritto");
        restaurante5.setCategoria("Italiana");
        restaurante5.setEndereco("Quadra 206 Sul Avenida NS 4");
        restaurante5.setTelefone("(63) 99663-8165");
        restaurante5.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante5.setAvaliacao(new BigDecimal("4.3"));
        restaurante5.setAtivo(true);
        restaurante5.setDataCriacao(LocalDateTime.now());

        Restaurante restaurante6 = new Restaurante();
        restaurante6.setNome("Suzaki Temaki");
        restaurante6.setCategoria("Japonesa");
        restaurante6.setEndereco("Rua João Walcacer de Oliveira");
        restaurante6.setTelefone("(98) 99439-6252");
        restaurante6.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante6.setAvaliacao(new BigDecimal("4.7"));
        restaurante6.setAtivo(true);
        restaurante6.setDataCriacao(LocalDateTime.now());

        restauranteRepository.saveAll(Arrays.asList(restaurante1, restaurante2, restaurante3,
                restaurante4,restaurante5,restaurante6));
    }

    private void inserirProdutos() {
        //Inserindo Produtos
        //-- Restaurante 1
        Restaurante restaurante1 = restauranteRepository.findById(1L)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: 1"));

        Produto produto1 = new Produto();
        produto1.setNome("Pizza Margherita");
        produto1.setDescricao("Molho de tomate, mussarela e manjericão");
        produto1.setPreco(BigDecimal.valueOf(35.90));
        produto1.setCategoria("Pizza");
        produto1.setDisponivel(true);
        produto1.setRestaurante(restaurante1);

        Produto produto2 = new Produto();
        produto2.setNome("Pizza Calabresa");
        produto2.setDescricao("Molho de tomate, mussarela e calabresa");
        produto2.setPreco(BigDecimal.valueOf(38.90));
        produto2.setCategoria("Pizza");
        produto2.setDisponivel(true);
        produto2.setRestaurante(restaurante1);


        Produto produto3 = new Produto();
        produto3.setNome("Lasanha Bolonhesa");
        produto3.setDescricao("Lasanha tradicional com molho bolonhesa");
        produto3.setPreco(BigDecimal.valueOf(28.90));
        produto3.setCategoria("Massa");
        produto3.setDisponivel(true);
        produto3.setRestaurante(restaurante1);


        //Restaurante 2
        Restaurante restaurante2 = restauranteRepository.findById(2L)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: 2"));

        Produto produto4 = new Produto();
        produto4.setNome("X-Burger");
        produto4.setDescricao("Hambúrguer, queijo, alface e tomate");
        produto4.setPreco(BigDecimal.valueOf(18.90));
        produto4.setCategoria("Hambúrguer");
        produto4.setDisponivel(true);
        produto4.setRestaurante(restaurante2);

        Produto produto5 = new Produto();
        produto5.setNome("X-Bacon");
        produto5.setDescricao("Hambúrguer, queijo, bacon, alface e tomate");
        produto5.setPreco(BigDecimal.valueOf(22.90));
        produto5.setCategoria("Hambúrguer");
        produto5.setDisponivel(true);
        produto5.setRestaurante(restaurante2);

        Produto produto6 = new Produto();
        produto6.setNome("Batata Frita");
        produto6.setDescricao("Porção de batata frita crocante");
        produto6.setPreco(BigDecimal.valueOf(12.90));
        produto6.setCategoria("Acompanhamento");
        produto6.setDisponivel(true);
        produto6.setRestaurante(restaurante2);

        //Restaurante 3
        Restaurante restaurante3 = restauranteRepository.findById(3L)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: 3"));

        Produto produto7 = new Produto();
        produto7.setNome("Combo Sashimi");
        produto7.setDescricao("15 peças de sashimi variado");
        produto7.setPreco(BigDecimal.valueOf(45.90));
        produto7.setCategoria("Sashimi");
        produto7.setDisponivel(true);
        produto7.setRestaurante(restaurante3);

        Produto produto8 = new Produto();
        produto8.setNome("Hot Roll Salmão");
        produto8.setDescricao("8 peças de hot roll de salmão");
        produto8.setPreco(BigDecimal.valueOf(22.90));
        produto8.setCategoria("Hot Roll");
        produto8.setDisponivel(true);
        produto8.setRestaurante(restaurante3);

        Produto produto9 = new Produto();
        produto9.setNome("Temaki Atum");
        produto9.setDescricao("Temaki de atum com cream cheese");
        produto9.setPreco(BigDecimal.valueOf(15.90));
        produto9.setCategoria("Temaki");
        produto9.setDisponivel(true);
        produto9.setRestaurante(restaurante3);

        //Restaurante 4
        Restaurante restaurante4 = restauranteRepository.findById(4L)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: 4"));
        Produto produto10 = new Produto();
        produto10.setNome("Batata Frita Metade Carne Seca Metade Costela com Catupiry");
        produto10.setDescricao("Batata Frita sequinha e crocante com metade carne seca desfiada e metade costela " +
                "com muito catupiry. É um verdadeiro paraíso da terra! ");
        produto10.setPreco(BigDecimal.valueOf(29.90));
        produto10.setCategoria("Acompanhamento");
        produto10.setDisponivel(true);
        produto10.setRestaurante(restaurante4);

        Produto produto11 = new Produto();
        produto11.setNome("Bacon Duplo");
        produto11.setDescricao("Pão brioche, 2 Hambúrgueres artesanais 200g, queijo cheddar, bacon crocante, " +
                "maionese verde e molho especial da casa.");
        produto11.setPreco(BigDecimal.valueOf(36.90));
        produto11.setCategoria("Lanche");
        produto11.setDisponivel(true);
        produto11.setRestaurante(restaurante4);

        Produto produto12 = new Produto();
        produto12.setNome("Turbo Queijo");
        produto12.setDescricao("Pão brioche, Hambúrguer artesanal 200g, Muito queijo prato, bacon crocante, " +
                "molho barbecue e molho especial da casa.");
        produto12.setPreco(BigDecimal.valueOf(28.90));
        produto12.setCategoria("Lanche");
        produto12.setDisponivel(true);
        produto12.setRestaurante(restaurante4);


        //-- Restaurante 5
        Optional<Restaurante> restaurante5 = restauranteRepository.findById(5L);

        Produto produto13 = new Produto();
        produto13.setNome("TORTELLI DI PECORINO TRE BICCHIERI");
        produto13.setDescricao("Massa fresca recheada com queijo pecorino e ricota, ao molho de manteiga de trufas.");
        produto13.setPreco(BigDecimal.valueOf(58.90));
        produto13.setCategoria("Massa");
        produto13.setDisponivel(true);
        produto13.setRestaurante(restaurante5.get());

        Produto produto14 = new Produto();
        produto14.setNome("RAVIOLI AL BRIE E MELE");
        produto14.setDescricao("Massa fresca recheada com queijo brie e compota de maçã verde, ao molho de manteiga, " +
                "sálvia e nozes.");
        produto14.setPreco(BigDecimal.valueOf(51.90));
        produto14.setCategoria("Massa");
        produto14.setDisponivel(true);
        produto14.setRestaurante(restaurante5.get());

        Produto produto15 = new Produto();
        produto15.setNome("LASAGNA DI BACCALÀ");
        produto15.setDescricao("Massa verde, lascas de bacalhau, brócolis, tomilho, cebola e molho de trufas.");
        produto15.setPreco(BigDecimal.valueOf(48.90));
        produto15.setCategoria("Massa");
        produto15.setDisponivel(true);
        produto15.setRestaurante(restaurante5.get());

        //-- Restaurante 6
        Optional<Restaurante> restaurante6 = restauranteRepository.findById(6L);

        Produto produto16 = new Produto();
        produto16.setNome("Tempurá de Legumes");
        produto16.setDescricao("Vegetais variados, empanados e fritos. Servidos com molho ponzu.");
        produto16.setPreco(BigDecimal.valueOf(21.90));
        produto16.setCategoria("Japonesa");
        produto16.setDisponivel(true);
        produto16.setRestaurante(restaurante6.get());

        Produto produto17 = new Produto();
        produto17.setNome("Camarão Empanado");
        produto17.setDescricao("Camarões grandes e empanados, servidos com molho tonkatsu.");
        produto17.setPreco(BigDecimal.valueOf(24.90));
        produto17.setCategoria("Japonesa");
        produto17.setDisponivel(true);
        produto17.setRestaurante(restaurante6.get());

        Produto produto18 = new Produto();
        produto18.setNome("Guioza ");
        produto18.setDescricao("Guioza de carne bovina ou de legumes, no vapor ou frito. Servido com molho ponzu.");
        produto18.setPreco(BigDecimal.valueOf(28.90));
        produto18.setCategoria("Japonesa");
        produto18.setDisponivel(true);
        produto18.setRestaurante(restaurante6.get());

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7,
                produto8, produto9,produto10,produto11,produto12,produto13,produto14,produto15,
                produto16,produto17,produto18));
    }

    private void inserirPedidos() {
        //Inserindo Pedidos
        //-- Pedido 1
        Optional<Produto> produto1 = produtoRepository.findById(1L);
        Optional<Produto> produto2 = produtoRepository.findById(3L);

        Optional<Cliente> cliente1 = clienteRepository.findById(1L);
        Optional<Restaurante> restaurante1 = restauranteRepository.findById(1L);

        ItemPedido itemPedido1 = new ItemPedido();
        itemPedido1.setPrecoUnitario(produto1.get().getPreco());
        itemPedido1.setProduto(produto1.get());
        itemPedido1.setQuantidade(1);
        itemPedido1.calcularSubTotal();

        ItemPedido itemPedido2 = new ItemPedido();
        itemPedido2.setPrecoUnitario(produto2.get().getPreco());
        itemPedido2.setProduto(produto2.get());
        itemPedido2.setQuantidade(1);
        itemPedido2.calcularSubTotal();

        BigDecimal subtotal = itemPedido1.getSubtotal().add(itemPedido2.getSubtotal());
        BigDecimal taxaEntrega = new BigDecimal("5.0");
        BigDecimal valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido = new Pedido();
        pedido.adicionarItem(itemPedido1);
        pedido.adicionarItem(itemPedido2);
        pedido.setNumeroPedido("PED1234567890");
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente1.get());
        pedido.setRestaurante(restaurante1.get());
        pedido.setEnderecoEntrega(cliente1.get().getEndereco());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTaxaEntrega(taxaEntrega);
        pedido.setValorTotal(valorTotal);
        pedido.setSubtotal(calcularSubtotal(pedido));
        pedido.setObservacoes("Sem cebola na pizza");

        itemPedido1.setPedido(pedido);
        itemPedido2.setPedido(pedido);
        pedidoRepository.save(pedido);

        //-- Pedido 2
        Optional<Produto> produto3 = produtoRepository.findById(4L);
        Optional<Produto> produto4 = produtoRepository.findById(5L);

        Optional<Cliente> cliente2 = clienteRepository.findById(2L);
        Optional<Restaurante> restaurante2 = restauranteRepository.findById(2L);

        ItemPedido itemPedido3 = new ItemPedido();
        itemPedido3.setPrecoUnitario(produto3.get().getPreco());
        itemPedido3.setProduto(produto3.get());
        itemPedido3.setQuantidade(1);
        itemPedido3.calcularSubTotal();

        ItemPedido itemPedido4 = new ItemPedido();
        itemPedido4.setPrecoUnitario(produto4.get().getPreco());
        itemPedido4.setProduto(produto4.get());
        itemPedido4.setQuantidade(1);
        itemPedido4.calcularSubTotal();


        subtotal = itemPedido3.getSubtotal().add(itemPedido4.getSubtotal());
        taxaEntrega = new BigDecimal("5.0");
        valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido2 = new Pedido();
        pedido2.adicionarItem(itemPedido3);
        pedido2.adicionarItem(itemPedido4);
        pedido2.setNumeroPedido("PED1234567891");
        pedido2.setDataPedido(LocalDateTime.now());
        pedido2.setCliente(cliente2.get());
        pedido2.setRestaurante(restaurante2.get());
        pedido2.setEnderecoEntrega(cliente2.get().getEndereco());
        pedido2.setStatus(StatusPedido.CONFIRMADO);
        pedido2.setTaxaEntrega(taxaEntrega);
        pedido2.setValorTotal(valorTotal);
        pedido2.setSubtotal(calcularSubtotal(pedido2));

        itemPedido3.setPedido(pedido2);
        itemPedido4.setPedido(pedido2);
        pedidoRepository.save(pedido2);

        //-- Pedido 3
        Optional<Produto> produto5 = produtoRepository.findById(7L);
        Optional<Produto> produto6 = produtoRepository.findById(8L);

        Optional<Cliente> cliente3 = clienteRepository.findById(3L);
        Optional<Restaurante> restaurante3 = restauranteRepository.findById(3L);

        ItemPedido itemPedido5 = new ItemPedido();
        itemPedido5.setPrecoUnitario(produto5.get().getPreco());
        itemPedido5.setProduto(produto5.get());
        itemPedido5.setQuantidade(1);
        itemPedido5.calcularSubTotal();

        ItemPedido itemPedido6 = new ItemPedido();
        itemPedido6.setPrecoUnitario(produto6.get().getPreco());
        itemPedido6.setProduto(produto6.get());
        itemPedido6.setQuantidade(1);
        itemPedido6.calcularSubTotal();

        subtotal = itemPedido5.getSubtotal().add(itemPedido6.getSubtotal());
        taxaEntrega = new BigDecimal("5.0");
        valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido3 = new Pedido();
        pedido3.adicionarItem(itemPedido5);
        pedido3.adicionarItem(itemPedido6);
        pedido3.setNumeroPedido("PED1234567892");
        pedido3.setDataPedido(LocalDateTime.now());
        pedido3.setCliente(cliente3.get());
        pedido3.setRestaurante(restaurante3.get());
        pedido3.setEnderecoEntrega(cliente3.get().getEndereco());
        pedido3.setStatus(StatusPedido.ENTREGUE);
        pedido3.setTaxaEntrega(taxaEntrega);
        pedido3.setValorTotal(valorTotal);
        pedido3.setSubtotal(calcularSubtotal(pedido3));
        pedido3.setObservacoes("Wasabi à parte");

        itemPedido5.setPedido(pedido3);
        itemPedido6.setPedido(pedido3);
        pedidoRepository.save(pedido3);

        // --------------------------------------------------------------
        //-- Pedido 4
        Optional<Produto> produto7 = produtoRepository.findById(10L);
        Optional<Produto> produto8 = produtoRepository.findById(12L);

        Optional<Cliente> cliente4 = clienteRepository.findById(4L);
        Optional<Restaurante> restaurante4 = restauranteRepository.findById(4L);

        ItemPedido itemPedido7 = new ItemPedido();
        itemPedido7.setPrecoUnitario(produto7.get().getPreco());
        itemPedido7.setProduto(produto7.get());
        itemPedido7.setQuantidade(2);
        itemPedido7.calcularSubTotal();

        ItemPedido itemPedido8 = new ItemPedido();
        itemPedido8.setPrecoUnitario(produto8.get().getPreco());
        itemPedido8.setProduto(produto8.get());
        itemPedido8.setQuantidade(3);
        itemPedido8.calcularSubTotal();

        subtotal = itemPedido7.getSubtotal().add(itemPedido8.getSubtotal());
        taxaEntrega = new BigDecimal("5.0");
        valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido4 = new Pedido();
        pedido4.adicionarItem(itemPedido7);
        pedido4.adicionarItem(itemPedido8);
        pedido4.setNumeroPedido("PED2025070701");
        pedido4.setDataPedido(LocalDateTime.now());
        pedido4.setCliente(cliente1.get());
        pedido4.setRestaurante(restaurante1.get());
        pedido4.setEnderecoEntrega(cliente1.get().getEndereco());
        pedido4.setStatus(StatusPedido.PENDENTE);
        pedido4.setTaxaEntrega(taxaEntrega);
        pedido4.setValorTotal(valorTotal);
        pedido4.setSubtotal(calcularSubtotal(pedido4));

        itemPedido7.setPedido(pedido4);
        itemPedido8.setPedido(pedido4);
        pedidoRepository.save(pedido4);

        // --------------------------------------------------------------
        //-- Pedido 5
        Optional<Produto> produto9 = produtoRepository.findById(16L);
        Optional<Produto> produto10 = produtoRepository.findById(18L);

        Optional<Cliente> cliente5 = clienteRepository.findById(6L);
        Optional<Produto> produto11 = produtoRepository.findById(17L);

        Optional<Restaurante> restaurante5 = restauranteRepository.findById(6L);

        ItemPedido itemPedido9 = new ItemPedido();
        itemPedido9.setPrecoUnitario(produto9.get().getPreco());
        itemPedido9.setProduto(produto9.get());
        itemPedido9.setQuantidade(1);
        itemPedido9.calcularSubTotal();

        ItemPedido itemPedido10 = new ItemPedido();
        itemPedido10.setPrecoUnitario(produto10.get().getPreco());
        itemPedido10.setProduto(produto10.get());
        itemPedido10.setQuantidade(3);
        itemPedido10.calcularSubTotal();

        ItemPedido itemPedido11 = new ItemPedido();
        itemPedido11.setPrecoUnitario(produto11.get().getPreco());
        itemPedido11.setProduto(produto11.get());
        itemPedido11.setQuantidade(3);
        itemPedido11.calcularSubTotal();

        subtotal = itemPedido9.getSubtotal().add(itemPedido10.getSubtotal()).add(itemPedido11.getSubtotal());
        taxaEntrega = new BigDecimal("10.90");
        valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido5 = new Pedido();
        pedido5.adicionarItem(itemPedido9);
        pedido5.adicionarItem(itemPedido10);
        pedido5.adicionarItem(itemPedido11);
        pedido5.setNumeroPedido("PED2025070702");
        pedido5.setDataPedido(LocalDateTime.now());
        pedido5.setCliente(cliente2.get());
        pedido5.setRestaurante(restaurante2.get());
        pedido5.setEnderecoEntrega(cliente2.get().getEndereco());
        pedido5.setStatus(StatusPedido.PENDENTE);
        pedido5.setTaxaEntrega(taxaEntrega);
        pedido5.setValorTotal(valorTotal);
        pedido5.setSubtotal(calcularSubtotal(pedido5));

        itemPedido9.setPedido(pedido5);
        itemPedido10.setPedido(pedido5);
        itemPedido11.setPedido(pedido5);
        pedidoRepository.save(pedido5);
    }

    private void testarConsultas() {
        System.out.println("\n=== TESTANDO CONSULTAS DOS REPOSITORIES ===");

        // Teste ClienteRepository
        System.out.println("\n--- Testes ClienteRepository ---");

        Optional<Cliente> clientePorEmail = clienteRepository.findByEmail("joao@email.com");
        System.out.println("Cliente por email: " +
                (clientePorEmail.isPresent() ? clientePorEmail.get().getNome() : "Não encontrado"));

        List<Cliente> clientesAtivos = clienteRepository.findByAtivoTrue();
        System.out.println("Clientes ativos: " + clientesAtivos.size());

        List<Cliente> clientesPorNome = clienteRepository.findByNomeContainingIgnoreCase("silva");
        System.out.println("Clientes com 'silva' no nome: " + clientesPorNome.size());

        boolean emailExiste = clienteRepository.existsByEmail("maria@email.com");
        System.out.println("Email maria@email.com existe: " + emailExiste);

        // Teste RestaurantRepository
        System.out.println("\n--- Testes RestauranteRepository ---");

        List<Restaurante> listaCategoriasAtivo = restauranteRepository.findByCategoriaAndAtivoTrue("Japonesa");
        System.out.println("Lista de Categorias Ativas");
        listaCategoriasAtivo.forEach(System.out::println);

        List<Restaurante> listaTaxaEntregaMenorIgual =
                restauranteRepository.findByTaxaEntregaLessThanEqual(new BigDecimal("5.0"));
        System.out.println("Lista de Taxa de Entrega Menor ou Igual a");
        listaTaxaEntregaMenorIgual.forEach(System.out::println);

        List<Restaurante> listaTop5Restaurantes = restauranteRepository.findTop5ByOrderByNomeAsc();
        listaTop5Restaurantes.forEach(System.out::println);

        // Teste ProdutoRepository
        System.out.println("\n--- Testes ProdutoRepository ---");
        List<Produto> listaProdutoPorRestaurante = produtoRepository.findByRestauranteIdAndDisponivelTrue(1L);
        System.out.println("Lista de Produtos Por Restaurante x");
        listaProdutoPorRestaurante.forEach(System.out::println);

        List<Produto> listaProdutosPorCategoria = produtoRepository.findByCategoriaAndDisponivelTrue("Lanche");
        System.out.println("Lista de Produtos Por Categoria");
        listaProdutosPorCategoria.forEach(System.out::println);

        List<Produto> listaProdutosMenorOuIgual =
                produtoRepository.findByPrecoLessThanEqualAndDisponivelTrue(new BigDecimal("35.90"));
        System.out.println("Lista de Produtos Menor ou Igual a");
        listaProdutosMenorOuIgual.forEach(System.out::println);

        // Teste PedidoRepository
        System.out.println("\n--- Testes PedidoRepository ---");
        List<Pedido> listaPedidosPorCliente = pedidoRepository.findByClienteIdOrderByDataPedidoDesc(4L);
        System.out.println("Lista de Pedidos Por Clientes");
        listaPedidosPorCliente.forEach(System.out::println);

        List<Pedido> listaStatusPedido = pedidoRepository.findByStatusOrderByDataPedidoDesc(StatusPedido.PENDENTE);
        System.out.println("Lista de Pedidos Por Status");
        listaStatusPedido.forEach(System.out::println);

        List<Pedido> listaTop10Pedidos= pedidoRepository.findTop10ByOrderByDataPedidoDesc();
        System.out.println("Lista Top 10 Pedidos");
        listaTop10Pedidos.forEach(System.out::println);

        List<Pedido> listaPedidosEntreDatas = pedidoRepository
                .findByDataPedidoBetweenOrderByDataPedidoDesc
                        (LocalDateTime.now(), LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        System.out.println("Lista de Pedidos Entre Datas");
        listaPedidosEntreDatas.forEach(System.out::println);
    }

    private BigDecimal calcularSubtotal(Pedido pedido) {
        BigDecimal totalPedido = new BigDecimal(0.0);
        for (ItemPedido item : pedido.getItens()) {
            totalPedido = totalPedido.add(item.getSubtotal());
        }
        return totalPedido;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void inserirUsuarios(){
        //ADMIN
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Admin Sistema");
        usuario1.setEmail("admin@admin.com");
        usuario1.setSenha(passwordEncoder().encode("123456"));
        usuario1.setRole(Role.ADMIN);
        usuario1.setAtivo(true);
        usuario1.setDataCriacao(LocalDateTime.now());
        usuario1.setRestauranteId(null);

        //CLIENTE 1
        Usuario usuario2 = new Usuario();
        usuario2.setNome("João Cliente");
        usuario2.setEmail("joaocliente@email.com");
        usuario2.setSenha(passwordEncoder().encode("123456"));
        usuario2.setRole(Role.CLIENTE);
        usuario2.setAtivo(true);
        usuario2.setDataCriacao(LocalDateTime.now());
        usuario2.setRestauranteId(null);

        //CLIENTE 2
        Usuario usuario3 = new Usuario();
        usuario3.setNome("Maria Cliente");
        usuario3.setEmail("mariacliente@email.com");
        usuario3.setSenha(passwordEncoder().encode("123456"));
        usuario3.setRole(Role.CLIENTE);
        usuario3.setAtivo(true);
        usuario3.setDataCriacao(LocalDateTime.now());
        usuario3.setRestauranteId(null);

        //RESTAURANTE 1
        Usuario usuario4 = new Usuario();
        usuario4.setNome("Pizza Palace");
        usuario4.setEmail("pizza@palace.com");
        usuario4.setSenha(passwordEncoder().encode("123456"));
        usuario4.setRole(Role.RESTAURANTE);
        usuario4.setAtivo(true);
        usuario4.setDataCriacao(LocalDateTime.now());
        usuario4.setRestauranteId(1L);

        //RESTAURANTE 2
        Usuario usuario5 = new Usuario();
        usuario5.setNome("Burger King");
        usuario5.setEmail("burger@king.com");
        usuario5.setSenha(passwordEncoder().encode("123456"));
        usuario5.setRole(Role.RESTAURANTE);
        usuario5.setAtivo(true);
        usuario5.setDataCriacao(LocalDateTime.now());
        usuario5.setRestauranteId(2L);

        //ENTREGADOR
        Usuario usuario6 = new Usuario();
        usuario6.setNome("Carlos Entregador");
        usuario6.setEmail("carlos@entrega.com");
        usuario6.setSenha(passwordEncoder().encode("123456"));
        usuario6.setRole(Role.ENTREGADOR);
        usuario6.setAtivo(true);
        usuario6.setDataCriacao(LocalDateTime.now());
        usuario6.setRestauranteId(null);

        usuarioRepository.saveAll(Arrays.asList(usuario1,usuario2,usuario3,usuario4,usuario5,usuario6));
    }

}

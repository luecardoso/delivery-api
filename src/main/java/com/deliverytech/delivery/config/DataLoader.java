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
//        pedidoRepository.deleteAll();
//        produtoRepository.deleteAll();
//        restauranteRepository.deleteAll();
//        clienteRepository.deleteAll();

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
        cliente1.setNome("Simone Lara Rocha");
        cliente1.setEmail("simone.lara.rocha@fanger.com.br");
        cliente1.setTelefone("(67) 93754-5597");
        cliente1.setEndereco("Avenida Clodoaldo Garcia");
        cliente1.setDataCadastro(LocalDateTime.now());
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Emilly Andreia Marina Fogaça");
        cliente2.setEmail("emilly.andreia.fogaca@nonesiglio.com.br");
        cliente2.setTelefone("(95) 99917-0433");
        cliente2.setEndereco("Rua Curimatã");
        cliente2.setDataCadastro(LocalDateTime.now());
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Heitor Juan Diego Lopes");
        cliente3.setEmail("heitor.juan.lopes@live.com.pt");
        cliente3.setTelefone("(92) 99470-0653");
        cliente3.setEndereco("Rua Tupaiú");
        cliente3.setDataCadastro(LocalDateTime.now());
        cliente3.setAtivo(true);

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
    }

    private void inserirRestaurantes() {
        //Inserindo Restaurantes
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Hamburgueria do Penguim");
        restaurante1.setCategoria("Hamburgueria");
        restaurante1.setEndereco("Rua Rio São Francisco");
        restaurante1.setTelefone("(92) 98111-3224");
        restaurante1.setTaxaEntrega(new BigDecimal("3.50"));
        restaurante1.setAvaliacao(new BigDecimal("4.0"));
        restaurante1.setAtivo(true);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Ristorante Fioritto");
        restaurante2.setCategoria("Italiana");
        restaurante2.setEndereco("Quadra 206 Sul Avenida NS 4");
        restaurante2.setTelefone("(63) 99663-8165");
        restaurante2.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante1.setAvaliacao(new BigDecimal("4.3"));
        restaurante2.setAtivo(true);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNome("Suzaki Temaki");
        restaurante3.setCategoria("Japonesa");
        restaurante3.setEndereco("Rua João Walcacer de Oliveira");
        restaurante3.setTelefone("(98) 99439-6252");
        restaurante3.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante1.setAvaliacao(new BigDecimal("4.7"));
        restaurante3.setAtivo(true);

        restauranteRepository.saveAll(Arrays.asList(restaurante1, restaurante2, restaurante3));
    }

    private void inserirProdutos() {
        //Inserindo Produtos
        //-- Restaurante 1
        Restaurante restaurante1 = restauranteRepository.findById(4L)
                .orElseThrow(() -> new BusinessException("Restaurante não encontrado com ID: "));

        Produto produto1 = new Produto();
        produto1.setNome("Batata Frita Metade Carne Seca Metade Costela com Catupiry");
        produto1.setDescricao("Batata Frita sequinha e crocante com metade carne seca desfiada e metade costela " +
                "com muito catupiry. É um verdadeiro paraíso da terra! ");
        produto1.setPreco(BigDecimal.valueOf(29.90));
        produto1.setCategoria("Acompanhamento");
        produto1.setDisponivel(true);
        produto1.setRestaurante(restaurante1);

        Produto produto2 = new Produto();
        produto2.setNome("Bacon Duplo");
        produto2.setDescricao("Pão brioche, 2 Hambúrgueres artesanais 200g, queijo cheddar, bacon crocante, " +
                "maionese verde e molho especial da casa.");
        produto2.setPreco(BigDecimal.valueOf(36.90));
        produto2.setCategoria("Lanche");
        produto2.setDisponivel(true);
        produto2.setRestaurante(restaurante1);

        Produto produto3 = new Produto();
        produto3.setNome("Turbo Queijo");
        produto3.setDescricao("Pão brioche, Hambúrguer artesanal 200g, Muito queijo prato, bacon crocante, " +
                "molho barbecue e molho especial da casa.");
        produto3.setPreco(BigDecimal.valueOf(28.90));
        produto3.setCategoria("Lanche");
        produto3.setDisponivel(true);
        produto3.setRestaurante(restaurante1);

        //-- Restaurante 2
        Optional<Restaurante> restaurante2 = restauranteRepository.findById(5L);

        Produto produto4 = new Produto();
        produto4.setNome("TORTELLI DI PECORINO TRE BICCHIERI");
        produto4.setDescricao("Massa fresca recheada com queijo pecorino e ricota, ao molho de manteiga de trufas.");
        produto4.setPreco(BigDecimal.valueOf(58.90));
        produto4.setCategoria("Massa");
        produto4.setDisponivel(true);
        produto4.setRestaurante(restaurante2.get());

        Produto produto5 = new Produto();
        produto5.setNome("RAVIOLI AL BRIE E MELE");
        produto5.setDescricao("Massa fresca recheada com queijo brie e compota de maçã verde, ao molho de manteiga, " +
                "sálvia e nozes.");
        produto5.setPreco(BigDecimal.valueOf(51.90));
        produto5.setCategoria("Massa");
        produto5.setDisponivel(true);
        produto5.setRestaurante(restaurante2.get());

        Produto produto6 = new Produto();
        produto6.setNome("LASAGNA DI BACCALÀ");
        produto6.setDescricao("Massa verde, lascas de bacalhau, brócolis, tomilho, cebola e molho de trufas.");
        produto6.setPreco(BigDecimal.valueOf(48.90));
        produto6.setCategoria("Massa");
        produto6.setDisponivel(true);
        produto6.setRestaurante(restaurante2.get());

        //-- Restaurante 3
        Optional<Restaurante> restaurante3 = restauranteRepository.findById(6L);

        Produto produto7 = new Produto();
        produto7.setNome("Tempurá de Legumes");
        produto7.setDescricao("Vegetais variados, empanados e fritos. Servidos com molho ponzu.");
        produto7.setPreco(BigDecimal.valueOf(21.90));
        produto7.setCategoria("Japonesa");
        produto7.setDisponivel(true);
        produto7.setRestaurante(restaurante3.get());

        Produto produto8 = new Produto();
        produto8.setNome("Camarão Empanado");
        produto8.setDescricao("Camarões grandes e empanados, servidos com molho tonkatsu.");
        produto8.setPreco(BigDecimal.valueOf(24.90));
        produto8.setCategoria("Japonesa");
        produto8.setDisponivel(true);
        produto8.setRestaurante(restaurante3.get());

        Produto produto9 = new Produto();
        produto9.setNome("Guioza ");
        produto9.setDescricao("Guioza de carne bovina ou de legumes, no vapor ou frito. Servido com molho ponzu.");
        produto9.setPreco(BigDecimal.valueOf(28.90));
        produto9.setCategoria("Japonesa");
        produto9.setDisponivel(true);
        produto9.setRestaurante(restaurante3.get());

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9));
    }

    private void inserirPedidos() {
        //Inserindo Pedidos
        //-- Pedido 1
        Optional<Produto> produto1 = produtoRepository.findById(10L);
        Optional<Produto> produto2 = produtoRepository.findById(12L);

        Optional<Cliente> cliente1 = clienteRepository.findById(4L);
        Optional<Restaurante> restaurante1 = restauranteRepository.findById(4L);

        ItemPedido itemPedido1 = new ItemPedido();
        itemPedido1.setPrecoUnitario(produto1.get().getPreco());
        itemPedido1.setProduto(produto1.get());
        itemPedido1.setQuantidade(2);
        itemPedido1.calcularSubTotal();

        ItemPedido itemPedido2 = new ItemPedido();
        itemPedido2.setPrecoUnitario(produto2.get().getPreco());
        itemPedido2.setProduto(produto2.get());
        itemPedido2.setQuantidade(3);
        itemPedido2.calcularSubTotal();

        BigDecimal subtotal = itemPedido1.getSubtotal().add(itemPedido2.getSubtotal());
        BigDecimal taxaEntrega = new BigDecimal("7.90");
        BigDecimal valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido = new Pedido();
        pedido.adicionarItem(itemPedido1);
        pedido.adicionarItem(itemPedido2);
        pedido.setNumeroPedido("PED2025070701");
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente1.get());
        pedido.setRestaurante(restaurante1.get());
        pedido.setEnderecoEntrega(cliente1.get().getEndereco());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setTaxaEntrega(taxaEntrega);
        pedido.setValorTotal(valorTotal);
        pedido.setSubtotal(calcularSubtotal(pedido));

        itemPedido1.setPedido(pedido);
        itemPedido2.setPedido(pedido);
        pedidoRepository.save(pedido);

        // --------------------------------------------------------------
        //-- Pedido 2
        Optional<Produto> produto3 = produtoRepository.findById(16L);
        Optional<Produto> produto4 = produtoRepository.findById(18L);

        Optional<Cliente> cliente2 = clienteRepository.findById(6L);
        Optional<Produto> produto5 = produtoRepository.findById(17L);

        Optional<Restaurante> restaurante2 = restauranteRepository.findById(6L);

        ItemPedido itemPedido3 = new ItemPedido();
        itemPedido3.setPrecoUnitario(produto3.get().getPreco());
        itemPedido3.setProduto(produto3.get());
        itemPedido3.setQuantidade(1);
        itemPedido3.calcularSubTotal();

        ItemPedido itemPedido4 = new ItemPedido();
        itemPedido4.setPrecoUnitario(produto4.get().getPreco());
        itemPedido4.setProduto(produto4.get());
        itemPedido4.setQuantidade(3);
        itemPedido4.calcularSubTotal();

        ItemPedido itemPedido5 = new ItemPedido();
        itemPedido5.setPrecoUnitario(produto5.get().getPreco());
        itemPedido5.setProduto(produto5.get());
        itemPedido5.setQuantidade(3);
        itemPedido5.calcularSubTotal();

        subtotal = itemPedido3.getSubtotal().add(itemPedido4.getSubtotal()).add(itemPedido5.getSubtotal());
        taxaEntrega = new BigDecimal("10.90");
        valorTotal = subtotal.add(taxaEntrega);

        Pedido pedido2 = new Pedido();
        pedido2.adicionarItem(itemPedido3);
        pedido2.adicionarItem(itemPedido4);
        pedido2.adicionarItem(itemPedido5);
        pedido2.setNumeroPedido("PED2025070702");
        pedido2.setDataPedido(LocalDateTime.now());
        pedido2.setCliente(cliente2.get());
        pedido2.setRestaurante(restaurante2.get());
        pedido2.setEnderecoEntrega(cliente2.get().getEndereco());
        pedido2.setStatus(StatusPedido.PENDENTE);
        pedido2.setTaxaEntrega(taxaEntrega);
        pedido2.setValorTotal(valorTotal);
        pedido2.setSubtotal(calcularSubtotal(pedido2));

        itemPedido3.setPedido(pedido2);
        itemPedido4.setPedido(pedido2);
        itemPedido5.setPedido(pedido2);
        pedidoRepository.save(pedido2);
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

package com.deliverytech.delivery.config;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.math.BigDecimal;

@TestConfiguration
public class TestDataConfiguration {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeTestMethod
    public void setupTestData() {
        // Limpar dados existentes
//        clienteRepository.deleteAll();
//        produtoRepository.deleteAll();

        // Criar dados de teste
        Cliente cliente = new Cliente();
        cliente.setNome("João Teste");
        cliente.setEmail("joao.teste@email.com");
        cliente.setCpf("12345678901");
        cliente.setTelefone("11999999999");
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setNome("Pizza Teste");
        produto.setDescricao("Pizza para testes");
        produto.setPreco(BigDecimal.valueOf(29.90));
        produto.setEstoque(50);
        produtoRepository.save(produto);
    }
}

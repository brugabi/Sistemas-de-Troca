"use client";
import { useState, useEffect } from 'react';
import Image from 'next/image';
import Navbar from '@components/navbar';
import PostCards from '@components/postcards';

export default function Proposals() {
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        const fetchCategorias = async () => {
            try {
                const response = await fetch('http://127.0.0.1:8080/api/departamentos/listar');
                if (response.ok) {
                    const data = await response.json();
                    setCategorias(data);
                } else {
                    console.error('Erro ao buscar categorias:', response.statusText);
                }
            } catch (error) {
                console.error('Erro ao buscar categorias:', error);
            }
        };

        fetchCategorias();
    }, []); // Executa apenas uma vez ao montar o componente

    return (
        <div className="flex flex-col min-h-screen bg-slate-100">
            <Navbar />

            <div className="flex flex-col w-[100%] overflow-y-auto">
                <PostCards />
            </div>
        </div>
    );
}

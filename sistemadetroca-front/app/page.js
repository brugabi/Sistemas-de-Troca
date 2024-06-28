"use client";
import { useState } from 'react';
import Image from "next/image";

export default function Login() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [birthdate, setBirthdate] = useState('');

  const handleSignup = async () => {
    try {
      const response = await fetch('url_do_seu_backend/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, email, password, birthdate }),
      });

      if (response.ok) {
        // Lógica para sucesso na criação de conta
        console.log('Conta criada com sucesso!');
      } else {
        // Lógica para tratamento de erro
        console.error('Falha ao criar conta:', response.statusText);
      }
    } catch (error) {
      console.error('Erro ao processar criação de conta:', error);
    }
  };

  return (
    <main className="flex h-dvh justify-center items-center">
      <div className="flex items-center w-[70rem] h-[40rem] rounded-2xl shadow-lg">

        <div className="flex bg-[#F26329] w-[25rem] h-full  justify-center rounded-s-2xl hover:w-[80rem] transition-all duration-500">
          <div className="w-full">
            <div className="flex flex-col justify-center items-center h-[30rem] gap-4">
              <div className="flex justify-center items-center font-bold h-[10rem]">
                <span className="text-white text-[35px]">Seja bem vindo!</span>
              </div>
              <div className="flex w-[50%] p-3 bg-slate-100 rounded-sm gap-1">
                <Image src="/icons/mail_black.svg" alt="User Icon" width={25} height={25} />
                <input type="email" className="w-[90%] bg-slate-100 focus:outline-none" placeholder="Email"></input>
              </div>
              <div className="flex w-[50%] p-3 bg-slate-100 rounded-sm gap-1">
                <Image src="/icons/lock_black.svg" alt="User Icon" width={25} height={25} />
                <input type="password" className="w-[90%] bg-slate-100 focus:outline-none" placeholder="Confirmar Senha"></input>
              </div>
              <div className="flex w-[50%] justify-center">
                <button className="p-2 w-[8rem] rounded-lg text-white border border-white">Login</button>
              </div>
            </div>
          </div>
        </div>

        <div className="w-[50rem] rounded-e-2xl">
          <div className="flex flex-col justify-center items-center h-[30rem] gap-4">
            <div className="flex flex-col justify-center items-center h-[10rem]">
              <span className="text-[#F26329] font-bold text-[35px]">Crie uma conta</span>
              <span className="text-slate-400">Insira as informações solicitadas abaixo</span>
            </div>
            <div className="flex w-[50%] p-3 bg-slate-100 gap-1">
              {/* Ícone de usuário */}
              <input
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-[90%] bg-slate-100 focus:outline-none"
                placeholder="Usuário"
              />
            </div>
            <div className="flex w-[50%] p-3 bg-slate-100 gap-1">
              {/* Ícone de email */}
              <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                type="email"
                className="w-[90%] bg-slate-100 focus:outline-none"
                placeholder="Email"
              />
            </div>
            <div className="flex w-[50%] p-3 bg-slate-100 gap-1">
              {/* Ícone de senha */}
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                type="password"
                className="w-[90%] bg-slate-100 focus:outline-none"
                placeholder="Senha"
              />
            </div>
            <div className="flex w-[50%] p-3 bg-slate-100 gap-1">
              {/* Ícone de calendário */}
              <input
                value={birthdate}
                onChange={(e) => setBirthdate(e.target.value)}
                type="date"
                className="w-[90%] bg-slate-100 focus:outline-none"
              />
            </div>
            <div className="flex w-[50%] justify-center">
              {/* Botão de criar conta */}
              <button onClick={handleSignup} className="bg-[#F26329] p-2 rounded-lg text-white">
                Criar Conta
              </button>
            </div>
          </div>
        </div>

      </div>
    </main>
  );
}


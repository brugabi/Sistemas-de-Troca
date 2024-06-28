import Image from "next/image";
import Navbar from '@components/navbar';
import PostCards from '@components/postcards';

export default function HomePage() {
    return (
        <div className="flex flex-col min-h-screen bg-slate-100">
            <Navbar />
            <div className="flex flex-row flex-1 overflow-hidden">
                <div className="flex flex-col w-[15%] bg-white shadow-lg gap-5 p-2">
                    <div className="flex flex-col gap-2">
                        <span className="font-bold">Categorias</span>

                        <div className="flex gap-1 ml-2">
                            <div className="bg-slate-400 rounded-full p-1 hover:bg-[#F26329]">
                                <Image src={"/icons/book_black.svg"} width={25} height={25} alt="Livros" />
                            </div>
                            <span className="flex items-center">Livros</span>
                        </div>

                        <div className="flex gap-1 ml-2">
                            <div className="bg-slate-400 rounded-full p-1 hover:bg-[#F26329]">
                                <Image src={"/icons/game_black.svg"} width={25} height={25} alt="Jogos" />
                            </div>
                            <span className="flex items-center">Jogos</span>
                        </div>

                        <div className="flex gap-1 ml-2">
                            <div className="bg-slate-400 rounded-full p-1 hover:bg-[#F26329]">
                                <Image src={"/icons/game_black.svg"} width={25} height={25} alt="Acessórios" />
                            </div>
                            <span className="flex items-center">Acessórios</span>
                        </div>
                    </div>
                </div>

                <div className="flex flex-col w-[90%] overflow-y-auto">
                    <PostCards />
                </div>
            </div>
        </div>
    );
}

import Image from "next/image";

const navBar = () => {
    return (
        <div className="flex items-center w-full h-[4rem] shadow-lg border-2 border-opacity-1 px-[3rem] bg-white">
            <div className="flex justify-center items-center w-[14rem]">
                <Image src="/troca-logo.png" alt="Logo" width={120} height={64} />
            </div>
        </div>
    );
};

export default navBar;